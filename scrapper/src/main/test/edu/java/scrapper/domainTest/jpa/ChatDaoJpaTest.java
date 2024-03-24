package edu.java.scrapper.domainTest.jpa;

import edu.java.domain.jpa.ChatDaoJpa;
import edu.java.domain.jpa.entity.ChatEntity;
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
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = { "app.database-access-type=jpa" })
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
@Transactional
@Rollback
@DirtiesContext
public class ChatDaoJpaTest extends IntegrationTest {

    @Autowired
    private ChatDaoJpa chatDaoJpa;

    @Test
    @Transactional
    void addTest() {
        Long id = 8L;
        chatDaoJpa.save(new ChatEntity(id));
        List<ChatEntity> chats = chatDaoJpa.findAll();
        assertEquals(1, chats.size());
        assertEquals(id, chats.get(0).getId());
    }

    @Test
    @Transactional
    void addTwoSameChatTest() {
        Long id = 9L;
        Long id2 = 10L;

        chatDaoJpa.save(new ChatEntity(id));
        chatDaoJpa.save(new ChatEntity(id2));

        List<ChatEntity> chats = chatDaoJpa.findAll();
        assertEquals(2, chats.size());
    }

    @Test
    @Transactional
    void removeTest() {
        Long id = 10L;
        chatDaoJpa.save(new ChatEntity(id));

        chatDaoJpa.deleteById(id);

        List<ChatEntity> chats = chatDaoJpa.findAll();
        assertEquals(0, chats.size());
    }


    @Test
    @Transactional
    void findAllByIdTest() {
        chatDaoJpa.save(new ChatEntity(12L));
        chatDaoJpa.save(new ChatEntity(13L));
        chatDaoJpa.save(new ChatEntity(14L));

        assertEquals(chatDaoJpa.findAll().size(), 3);
        assertEquals(chatDaoJpa.findById(12L).get().getId(), 12L);
        assertEquals(chatDaoJpa.findById(13L).get().getId(), 13L);
        assertEquals(chatDaoJpa.findById(14L).get().getId(), 14L);
    }

    @AfterEach
    @Transactional
    void clear() {
        chatDaoJpa.deleteAll();
    }
}
