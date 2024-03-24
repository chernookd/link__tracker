package edu.java.scrapper.domainTest.jdbc;

import edu.java.domain.jdbc.ChatDaoJdbc;
import edu.java.domain.dto.Chat;
import edu.java.scrapper.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = ChatDaoJdbc.class)
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
@Transactional
@Rollback
@DirtiesContext
public class ChatDaoJdbcTest extends IntegrationTest {

    @Autowired
    private ChatDaoJdbc chatDaoJdbc;

    @Test
    @Transactional
    void addTest() {
        Long id = 8L;
        chatDaoJdbc.add(id);
        List<Chat> chats = chatDaoJdbc.findAll();
        assertEquals(1, chats.size());
        assertEquals(id, chats.getFirst().getId());
    }

    @Test
    @Transactional
    void addTwoSameChatTest() {
        Long id = 9L;
        chatDaoJdbc.add(id);
        assertFalse(chatDaoJdbc.add(id));
    }

    @Test
    @Transactional
    void removeTest() {
        Long id = 10L;
        chatDaoJdbc.add(id);

        chatDaoJdbc.remove(id);

        List<Chat> chats = chatDaoJdbc.findAll();
        System.out.println(chatDaoJdbc.findAll());
        assertEquals(0, chats.size());
    }

    @Test
    @Transactional
    void removeEmptyChatTest() {
        Long id = 11L;
        chatDaoJdbc.add(id);

        chatDaoJdbc.remove(50L);

        List<Chat> chats = chatDaoJdbc.findAll();
        System.out.println(chatDaoJdbc.findAll());
        assertEquals(1, chats.size());
    }

    @Test
    @Transactional
    void findAllByIdTest() {
        chatDaoJdbc.add(12L);
        chatDaoJdbc.add(13L);
        chatDaoJdbc.add(14L);

       assertEquals(chatDaoJdbc.findById(12L), List.of(new Chat(12L)));
       assertEquals(chatDaoJdbc.findById(13L), List.of(new Chat(13L)));
       assertEquals(chatDaoJdbc.findById(14L), List.of(new Chat(14L)));
    }

    @AfterEach
    @Transactional
    void clear() {
        chatDaoJdbc.remove(8L);
        chatDaoJdbc.remove(9L);
        chatDaoJdbc.remove(10L);
        chatDaoJdbc.remove(11L);
        chatDaoJdbc.remove(12L);
        chatDaoJdbc.remove(13L);
        chatDaoJdbc.remove(14L);
        chatDaoJdbc.remove(50L);
    }

}
