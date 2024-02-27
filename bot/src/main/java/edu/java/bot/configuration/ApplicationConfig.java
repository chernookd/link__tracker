package edu.java.bot.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@PropertySource("application.yml")
@Data
public class ApplicationConfig {

    private final TelegramBotProperties telegramProperties;

    @Autowired
    public ApplicationConfig(TelegramBotProperties telegramProperties) {
        this.telegramProperties = telegramProperties;
    }

}


