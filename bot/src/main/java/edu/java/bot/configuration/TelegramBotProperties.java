package edu.java.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "telegram")
@PropertySource("application.yml")
@Data
public class TelegramBotProperties {
    @NotEmpty
    private String telegramToken;
}
