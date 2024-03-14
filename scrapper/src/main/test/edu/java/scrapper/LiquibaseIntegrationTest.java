package edu.java.scrapper;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LiquibaseIntegrationTest extends IntegrationTest{
    @Test
    void contextLoads() {
        assertThat(POSTGRES.isRunning()).isTrue();
    }
}
