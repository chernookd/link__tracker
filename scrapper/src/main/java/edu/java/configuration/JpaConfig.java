package edu.java.configuration;

import edu.java.botClient.BotClient;
import edu.java.clients.github.GithubClient;
import edu.java.clients.stackoverflow.StackoverflowClient;
import edu.java.domain.jpa.ChatDaoJpa;
import edu.java.domain.jpa.ChatLinkDaoJpa;
import edu.java.domain.jpa.LinkDaoJpa;
import edu.java.service.serviceDao.jpa.LinkServiceJpa;
import edu.java.service.serviceDao.jpa.TgChatServiceJpa;
import edu.java.service.update.jpa.LinkUpdaterJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
@EnableJpaRepositories(basePackages = "edu.java.domain.jpa")
public class JpaConfig {

    private final ChatLinkDaoJpa chatLinkDaoJpa;
    private final LinkDaoJpa linkDaoJpa;
    private final ChatDaoJpa chatDaoJpa;
    private final GithubClient githubClient;
    private final StackoverflowClient stackoverflowClient;
    private final BotClient botClient;

    @Bean
    public LinkServiceJpa linkServiceJpa() {
        return new LinkServiceJpa(linkDaoJpa, chatDaoJpa, chatLinkDaoJpa);
    }

    @Bean
    public TgChatServiceJpa tgChatServiceJpa() {
        return new TgChatServiceJpa(chatDaoJpa);
    }

    @Bean
    public LinkUpdaterJpa linkUpdaterJpa() {
        return new LinkUpdaterJpa(linkDaoJpa, chatLinkDaoJpa, githubClient, stackoverflowClient, botClient);
    }
}

