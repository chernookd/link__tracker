package edu.java.scrapper.domainTest;

import edu.java.domain.dao.ChatDao;
import edu.java.domain.dao.LinkDao;
import edu.java.domain.dto.Chat;
import edu.java.scrapper.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = ChatDao.class)
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
@Transactional
@Rollback
@DirtiesContext
public class ChatDaoTest extends IntegrationTest {

    @Autowired
    private ChatDao chatDao;

    @Test
    @Transactional
    void addTest() {
        Long id = 8L;
        chatDao.add(id);
        List<Chat> chats = chatDao.findAll();
        assertEquals(1, chats.size());
        assertEquals(id, chats.getFirst().getId());
    }

    @Test
    @Transactional
    void addTwoSameChatTest() {
        Long id = 9L;
        chatDao.add(id);
        assertFalse(chatDao.add(id));
    }

    @Test
    @Transactional
    void removeTest() {
        Long id = 10L;
        chatDao.add(id);

        chatDao.remove(id);

        List<Chat> chats = chatDao.findAll();
        System.out.println(chatDao.findAll());
        assertEquals(0, chats.size());
    }

    @Test
    @Transactional
    void removeEmptyChatTest() {
        Long id = 11L;
        chatDao.add(id);

        chatDao.remove(50L);

        List<Chat> chats = chatDao.findAll();
        System.out.println(chatDao.findAll());
        assertEquals(1, chats.size());
    }

    @Test
    @Transactional
    void findAllByIdTest() {
        chatDao.add(12L);
        chatDao.add(13L);
        chatDao.add(14L);

       assertEquals(chatDao.findById(12L), List.of(new Chat(12L)));
       assertEquals(chatDao.findById(13L), List.of(new Chat(13L)));
       assertEquals(chatDao.findById(14L), List.of(new Chat(14L)));
    }

    @AfterEach
    @Transactional
    void clear() {
        chatDao.remove(8L);
        chatDao.remove(9L);
        chatDao.remove(10L);
        chatDao.remove(11L);
        chatDao.remove(12L);
        chatDao.remove(13L);
        chatDao.remove(14L);
        chatDao.remove(50L);
    }

}
