package edu.java.service.update.jpa;

import edu.java.botClient.BotClient;
import edu.java.clients.github.GithubClient;
import edu.java.clients.github.dto.GithubResponse;
import edu.java.clients.stackoverflow.StackoverflowClient;
import edu.java.clients.stackoverflow.dto.StackoverflowItem;
import edu.java.clients.stackoverflow.dto.StackoverflowResponse;
import edu.java.domain.jpa.ChatLinkDaoJpa;
import edu.java.domain.jpa.LinkDaoJpa;
import edu.java.domain.jpa.entity.ChatLinkEntity;
import edu.java.domain.jpa.entity.LinkEntity;
import edu.java.service.update.LinkUpdater;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class LinkUpdaterJpa implements LinkUpdater {

    private final LinkDaoJpa linkDaoJpa;
    private final ChatLinkDaoJpa chatLinkDaoJpa;
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

        List<LinkEntity> checkLinkList = linkDaoJpa
            .findLinkEntitiesByUpdateTimeLessThanEqual(offsetDateTimeNow.minusSeconds(UPDATE_TIME));

        for (LinkEntity link : checkLinkList) {
            String linkUrl = link.getUrl();

            if (linkUrl.contains(GITHUB)) {
                Matcher matcher = patternGitHub.matcher(linkUrl);
                if (matcher.find()) {
                    String owner = matcher.group(1);
                    String repos = matcher.group(2);
                    GithubResponse githubResponse = githubClient.fetch(owner, repos);

                    List<ChatLinkEntity> chatLinkList = chatLinkDaoJpa.findAllByLink(link);
                    List<Long> chatIdList = chatLinkList.stream()
                        .map(chatLinkEntity -> chatLinkEntity.getChat().getId())
                        .toList();

                    if (githubResponse.getUpdatedAt().isAfter(link.getUpdateTime())) {
                        botClient.updateLink(link.getId(), link.getUrl(), DESCRIPTION, chatIdList);
                    }
                }
            } else if (linkUrl.contains(STACKOVERFLOW)) {
                Matcher matcher = patternStackoverflow.matcher(linkUrl);
                if (matcher.find()) {
                    String questionId = matcher.group(1);
                    StackoverflowResponse stackoverflowResponse = stackoverflowClient.fetch(Long.parseLong(questionId));

                    List<ChatLinkEntity> chatLinkList = chatLinkDaoJpa.findAllByLink(link);
                    List<Long> chatIdList = chatLinkList.stream()
                        .map(chatLinkEntity -> chatLinkEntity.getChat().getId())
                        .toList();

                    for (StackoverflowItem item : stackoverflowResponse.getItems()) {
                        if (item.getLastEditDate().isAfter(offsetDateTimeNow)) {
                            botClient.updateLink(link.getId(), link.getUrl(), DESCRIPTION, chatIdList);
                            break;
                        }
                    }
                }
            }

            link.setUpdateTime(offsetDateTimeNow);
            linkDaoJpa.save(link);
        }

        return 1;
    }
}
