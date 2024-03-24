package edu.java.configuration;


import edu.java.botClient.BotClient;
import edu.java.clients.github.GithubClient;
import edu.java.clients.stackoverflow.StackoverflowClient;
import edu.java.domain.jdbc.ChatDaoJdbc;
import edu.java.domain.jdbc.ChatLinkDaoJdbc;
import edu.java.domain.jdbc.LinkDaoJdbc;
import edu.java.service.serviceDao.jdbc.LinkServiceJdbc;
import edu.java.service.serviceDao.jdbc.TgChatServiceJdbc;
import edu.java.service.update.jdbc.LinkUpdaterJdbc;
import javax.sql.DataSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "data-source")
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@Getter
@Setter
public class JdbcConfig {

    private String url;
    private String username;
    private String password;

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource(url, username, password);
    }

    @Bean
    public ChatDaoJdbc chatDaoJdbc(JdbcTemplate jdbcTemplate) {
        return new ChatDaoJdbc(jdbcTemplate);
    }

    @Bean
    public ChatLinkDaoJdbc chatLinkDaoJdbc(JdbcTemplate jdbcTemplate) {
        return new ChatLinkDaoJdbc(jdbcTemplate);
    }

    @Bean
    public LinkDaoJdbc linkDaoJdbc(JdbcTemplate jdbcTemplate) {
        return new LinkDaoJdbc(jdbcTemplate);
    }

    @Bean
    public LinkServiceJdbc linkServiceJdbc(ChatLinkDaoJdbc chatLinkDaoJdbc,
        LinkDaoJdbc linkDaoJdbc,
        ChatDaoJdbc chatDaoJdbc) {
        return new LinkServiceJdbc(linkDaoJdbc, chatDaoJdbc, chatLinkDaoJdbc);
    }

    @Bean
    public TgChatServiceJdbc tgChatServiceJdbc(ChatDaoJdbc chatDaoJdbc) {
        return new TgChatServiceJdbc(chatDaoJdbc);
    }

    @Bean
    public LinkUpdaterJdbc linkUpdaterJdbc(LinkDaoJdbc linkDaoJdbc,
        GithubClient githubClient,
        ChatLinkDaoJdbc chatLinkDaoJdbc,
        StackoverflowClient stackoverflowClient,
        BotClient botClient
        ) {
        return new LinkUpdaterJdbc(linkDaoJdbc, chatLinkDaoJdbc, githubClient, stackoverflowClient, botClient);
    }

}
