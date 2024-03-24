package edu.java.scrapper.domainTest.jdbc;

import edu.java.domain.jdbc.ChatDaoJdbc;
import edu.java.domain.jdbc.ChatLinkDaoJdbc;
import edu.java.domain.jdbc.LinkDaoJdbc;
import edu.java.domain.dto.Link;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.time.OffsetDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {ChatLinkDaoJdbc.class, ChatDaoJdbc.class, LinkDaoJdbc.class})
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
@Transactional
@Rollback
@DirtiesContext
@Disabled
public class ChatLinkDaoJdbcTest extends IntegrationTest {

    @Autowired
    private ChatLinkDaoJdbc chatLinkDaoJdbc;
    @Autowired
    private ChatDaoJdbc chatDaoJdbc;
    @Autowired
    private LinkDaoJdbc linkDao;

    @Test
    @Transactional
    void addTest() {
        Long chatId = 1000L;
        Long linkId = 1000L;

        chatDaoJdbc.add(chatId);
        linkDao.add(URI.create("https://github.com/chernookd/link__tracker/pull/1"));
        chatLinkDaoJdbc.add(chatId, linkId);

        assertThat(chatLinkDaoJdbc.findAllLinks(chatId).size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void correctRemoveTest() {
        Long chatId = 2000L;
        Long linkId = 2000L;

        chatDaoJdbc.add(chatId);
        linkDao.add(URI.create("https://github.com/chernookd/link__tracker/pull/1"));
        chatLinkDaoJdbc.add(chatId, linkId);

        assertThat(chatLinkDaoJdbc.findAllLinks(chatId).size()).isEqualTo(1);

        chatLinkDaoJdbc.remove(chatId, linkId);

        assertThat(chatLinkDaoJdbc.findAllLinks(chatId).size()).isEqualTo(0);
    }


    @Test
    @Transactional
    void RemoveEmptyChatTest() {
        Long chatId = 3000L;
        Long linkId = 3000L;
        Long chatId2 = 4000L;
        Long linkId2 = 4000L;

        chatDaoJdbc.add(chatId);
        linkDao.add(URI.create("https://github.com/chernookd/link__tracker/pull/1"));
        chatLinkDaoJdbc.add(chatId, linkId);

        assertThat(chatLinkDaoJdbc.findAllLinks(chatId).size()).isEqualTo(1);

        chatLinkDaoJdbc.remove(chatId2, linkId2);

        assertThat(chatLinkDaoJdbc.findAllLinks(chatId).size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void findAllByLinkIdTest() {
        Long chatId = 5000L;
        Link link1 = new Link(5000, URI.create("https://github.com/chernookd/link__tracker/pull/1"), OffsetDateTime.now());
        Link link2 = new Link(6000, URI.create("https://github.com/chernookd/link__tracker/pull/2"), OffsetDateTime.now());;

        chatDaoJdbc.add(chatId);
        linkDao.add(link1);
        linkDao.add(link2);
        chatLinkDaoJdbc.add(chatId, 5000L);
        chatLinkDaoJdbc.add(chatId, 6000L);


        assertThat(chatLinkDaoJdbc.findAllChatByLink(5000L).size()).isEqualTo(1);
        assertThat(chatLinkDaoJdbc.findAllChatByLink(6000L).size()).isEqualTo(1);

    }

    @AfterEach
    @Transactional
    void clear() {
        chatLinkDaoJdbc.remove(1000L, 1000L);
        chatLinkDaoJdbc.remove(2000L, 2000L);
        chatLinkDaoJdbc.remove(3000L, 3000L);
        chatLinkDaoJdbc.remove(4000L, 4000L);
        chatLinkDaoJdbc.remove(5000L, 5000L);
        chatLinkDaoJdbc.remove(5000L, 6000L);
        chatLinkDaoJdbc.remove(6000L, 6000L);
        chatLinkDaoJdbc.remove(7000L, 7000L);

    }
}
