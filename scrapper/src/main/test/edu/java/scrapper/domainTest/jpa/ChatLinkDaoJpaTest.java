package edu.java.scrapper.domainTest.jpa;

import edu.java.domain.jpa.ChatDaoJpa;
import edu.java.domain.jpa.ChatLinkDaoJpa;
import edu.java.domain.jpa.LinkDaoJpa;
import edu.java.domain.jpa.entity.ChatEntity;
import edu.java.domain.jpa.entity.ChatLinkEntity;
import edu.java.domain.jpa.entity.LinkEntity;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = { "app.database-access-type=jpa" })
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
@Transactional
@Rollback
@DirtiesContext
public class ChatLinkDaoJpaTest extends IntegrationTest {

    @Autowired
    private ChatLinkDaoJpa chatLinkDaoJpa;
    @Autowired
    private ChatDaoJpa chatDaoJpa;
    @Autowired
    private LinkDaoJpa linkDaoJpa;

    @Test
    @Transactional
    void addTwoSameChatLinkTest() {
        Long chatId = 2L;

        ChatEntity chat = new ChatEntity(chatId);
        LinkEntity link = new LinkEntity(URI.create("https://github.com/chernookd/link__tracker/pull/2"));

        chatDaoJpa.save(chat);
        linkDaoJpa.save(link);
        chatLinkDaoJpa.save(new ChatLinkEntity(chat, link));
        chatDaoJpa.save(chat);
        linkDaoJpa.save(link);
        chatLinkDaoJpa.save(new ChatLinkEntity(chat, link));

        assertThat(chatLinkDaoJpa.findAllByChat(new ChatEntity(chatId)).size()).isEqualTo(2);
    }

    @Test
    @Transactional
    void removeTest() {
        Long chatId = 3L;

        ChatEntity chat = new ChatEntity(chatId);
        LinkEntity link = new LinkEntity(URI.create("https://github.com/chernookd/link__tracker/pull/3"));

        chatDaoJpa.save(chat);
        linkDaoJpa.save(link);
        chatLinkDaoJpa.save(new ChatLinkEntity(chat, link));

        chatLinkDaoJpa.deleteByChatAndLink(chat, link);

        List<ChatLinkEntity> chatLinks = chatLinkDaoJpa.findAllByChat(chat);
        assertEquals(0, chatLinks.size());
    }

    @Test
    @Transactional
    void findAllByChatTest() {
        ChatEntity chat1 = new ChatEntity(4L);
        ChatEntity chat2 = new ChatEntity(5L);
        LinkEntity link1 = new LinkEntity(URI.create("https://github.com/chernookd/link__tracker/pull/4"));
        LinkEntity link2 = new LinkEntity(URI.create("https://github.com/chernookd/link__tracker/pull/5"));

        chatDaoJpa.save(chat1);
        chatDaoJpa.save(chat2);
        linkDaoJpa.save(link1);
        linkDaoJpa.save(link2);
        chatLinkDaoJpa.save(new ChatLinkEntity(chat1, link1));
        chatLinkDaoJpa.save(new ChatLinkEntity(chat1, link2));
        chatLinkDaoJpa.save(new ChatLinkEntity(chat2, link1));

        List<ChatLinkEntity> chatLinks = chatLinkDaoJpa.findAllByChat(chat1);
        assertEquals(2, chatLinks.size());
    }

    @AfterEach
    @Transactional
    void clear() {
        chatLinkDaoJpa.deleteAll();
        chatDaoJpa.deleteAll();
        linkDaoJpa.deleteAll();
    }
}
