package edu.java.service.update.jdbc;

import edu.java.botClient.BotClient;
import edu.java.clients.github.GithubClient;
import edu.java.clients.github.dto.GithubResponse;
import edu.java.clients.stackoverflow.StackoverflowClient;
import edu.java.clients.stackoverflow.dto.StackoverflowItem;
import edu.java.clients.stackoverflow.dto.StackoverflowResponse;
import edu.java.domain.dao.ChatLinkDao;
import edu.java.domain.dao.LinkDao;
import edu.java.domain.dto.ChatLink;
import edu.java.domain.dto.Link;
import edu.java.service.serviceDao.LinkUpdater;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkUpdaterJdbc implements LinkUpdater {

    private final LinkDao linkDao;
    private final ChatLinkDao chatLinkDao;
    private final GithubClient githubClient;
    private final StackoverflowClient stackoverflowClient;
    private final BotClient botClient;
    private final static String GITHUB_REGEX = "github\\.com/([\\w-_.]+)/([\\w-_.]+)";
    private final static String STACKOVERFLOW_REGEX = "stackoverflow\\.com/questions/(\\d+)";
    private final static String GITHUB = "github";
    private final static String STACKOVERFLOW = "stackoverflow";
    private final static Long UPDATE_TIME = 10L;
    private final static String DESCRIPTION = "description";


    @Override
    public int update() {
        OffsetDateTime offsetDateTimeNow = OffsetDateTime.now();
        Pattern patternGitHub = Pattern.compile(GITHUB_REGEX);
        Pattern patternStackoverflow = Pattern.compile(STACKOVERFLOW_REGEX);

        List<Link> checkLinkList = linkDao.findByCheckTime(offsetDateTimeNow.minusSeconds(UPDATE_TIME));

        for (Link link : checkLinkList) {
            String linkUrl = link.getUrl().toString();

            if (linkUrl.contains(GITHUB)) {
                Matcher matcher = patternGitHub.matcher(linkUrl);
                if (matcher.find()) {
                    String owner = matcher.group(1);
                    String repos = matcher.group(2);
                    GithubResponse githubResponse = githubClient.fetch(owner, repos);

                    List<ChatLink> chatLinkList = chatLinkDao.findAllChatByLink(link.getId());
                    List<Long> chatIdList = chatLinkList.stream()
                        .map(ChatLink::getChatId).toList();

                    if (githubResponse.getUpdatedAt().isAfter(link.getUpdate_time())) {
                        botClient.updateLink(link.getId(), link.getUrl().toString(), DESCRIPTION, chatIdList);
                    }
                }
            } else if (linkUrl.contains(STACKOVERFLOW)) {
                Matcher matcher = patternStackoverflow.matcher(linkUrl);
                if (matcher.find()) {
                    boolean isThereItemThatBeenUpdated;
                    String questionId = matcher.group(1);
                    StackoverflowResponse stackoverflowResponse = stackoverflowClient.fetch(Long.parseLong(questionId));

                    List<ChatLink> chatLinkList = chatLinkDao.findAllChatByLink(link.getId());
                    List<Long> chatIdList = chatLinkList.stream()
                        .map(ChatLink::getChatId).toList();

                    for (StackoverflowItem item : stackoverflowResponse.getItems()) {
                        if (item.getLastEditDate().isAfter(offsetDateTimeNow)) {
                            botClient.updateLink(link.getId(), link.getUrl().toString(), DESCRIPTION, chatIdList);
                        }
                    }

                }
            }

            link.setUpdate_time(offsetDateTimeNow);
            linkDao.remove(link.getUrl());
            linkDao.add(link);
        }

        return 1;
    }
}
