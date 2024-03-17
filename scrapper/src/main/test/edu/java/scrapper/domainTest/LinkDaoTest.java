package edu.java.scrapper.domainTest;


import edu.java.domain.dao.LinkDao;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import edu.java.domain.dto.Link;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.net.URI;
import java.time.OffsetDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {LinkDao.class})
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
@Transactional
@Rollback
@DirtiesContext
public class LinkDaoTest extends IntegrationTest {

    @Autowired
    private LinkDao linkDao;

    @Test
    @Transactional
    void addTest() {
        OffsetDateTime now = OffsetDateTime.now().withNano(0);
        Link link = new Link(100L,URI.create("https://github.com/chernookd/link__tracker/pull/1"), now);

        linkDao.add(link);
        linkDao.add(URI.create("https://github.com/chernookd/link__tracker/pull/2"));

        assertThat(linkDao.findByCheckTime(now).size()).isEqualTo(1);
        assertThat(linkDao.findByUri(URI.create("https://github.com/chernookd/link__tracker/pull/2")).getUrl().toString())
            .isEqualTo("https://github.com/chernookd/link__tracker/pull/2");
        assertThat(linkDao.findByUri(URI.create("https://github.com/chernookd/link__tracker/pull/1")).getId())
            .isEqualTo(100L);
        assertThat(linkDao.findByUri(URI.create("https://github.com/chernookd/link__tracker/pull/1")).getUrl().toString())
            .isEqualTo("https://github.com/chernookd/link__tracker/pull/1");
    }

    @Test
    @Transactional
    void correctRemoveTest() {
        OffsetDateTime now = OffsetDateTime.now().withNano(0);
        Link link = new Link(101L,URI.create("https://github.com/chernookd/link__tracker/pull/1"), now);

        linkDao.add(link);

        assertThat(linkDao.findByCheckTime(now).size()).isEqualTo(1);

        linkDao.remove(URI.create("https://github.com/chernookd/link__tracker/pull/1"));

        assertThat(linkDao.findByCheckTime(now).size()).isEqualTo(0);
    }


    @AfterEach
    @Transactional
    void clear() {
        linkDao.remove(URI.create("https://github.com/chernookd/link__tracker/pull/1"));
        linkDao.remove(URI.create("https://github.com/chernookd/link__tracker/pull/2"));
        linkDao.remove(URI.create("https://github.com/chernookd/link__tracker/pull/2"));
    }

}
