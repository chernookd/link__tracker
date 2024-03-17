package edu.java.configuration;


import javax.sql.DataSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "data-source")
public class JdbcConfig {

    @Setter @Getter private String url;
    @Setter @Getter private String username;
    @Setter @Getter private String password;

    @Bean
    public DataSource dataSource() {
        DataSource dataSource = new DriverManagerDataSource(url, username, password);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
