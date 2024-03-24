package edu.java.scrapper.domainTest.jpa;

import edu.java.domain.jpa.LinkDaoJpa;
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
import java.time.OffsetDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(properties = { "app.database-access-type=jpa" })
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
@Transactional
@Rollback
@DirtiesContext
public class LinkDaoJpaTest extends IntegrationTest {

    @Autowired
    private LinkDaoJpa linkDaoJpa;

    @Test
    @Transactional
    void addTest() {
        linkDaoJpa.deleteAll();
        OffsetDateTime now = OffsetDateTime.now().withNano(0);
        LinkEntity link = new LinkEntity(1L, "https://github.com/chernookd/link__tracker/pull/1", now);
        LinkEntity link2 = new LinkEntity(URI.create("https://github.com/chernookd/link__tracker/pull/2"));

        linkDaoJpa.save(link);
        linkDaoJpa.save(link2);

        assertThat(linkDaoJpa.findLinkEntitiesByUpdateTimeLessThanEqual(now).size()).isEqualTo(2);
        assertThat(linkDaoJpa.findByUrl("https://github.com/chernookd/link__tracker/pull/2").get().getUrl())
            .isEqualTo("https://github.com/chernookd/link__tracker/pull/2");
        assertThat(linkDaoJpa.findByUrl("https://github.com/chernookd/link__tracker/pull/1").get().getUrl())
            .isEqualTo("https://github.com/chernookd/link__tracker/pull/1");
    }

    @Test
    @Transactional
    void correctRemoveTest() {
        OffsetDateTime now = OffsetDateTime.now().withNano(0);
        LinkEntity link = new LinkEntity(101L, "https://github.com/chernookd/link__tracker/pull/1", now);

        linkDaoJpa.save(link);

        assertThat(linkDaoJpa.findAll().size()).isEqualTo(1);

        linkDaoJpa.deleteByUrl("https://github.com/chernookd/link__tracker/pull/1");

        assertThat(linkDaoJpa.findAll().size()).isEqualTo(0);
    }

    @AfterEach
    @Transactional
    void clear() {
        linkDaoJpa.deleteAll();
    }
}
