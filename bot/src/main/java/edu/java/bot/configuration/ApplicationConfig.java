package edu.java.bot.configuration;

import edu.java.bot.core.TrackList;
import jakarta.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@PropertySource("application.yml")
@Data
@SuppressWarnings("ImportOrder")
public class ApplicationConfig {

    @NotEmpty
    @Value("${telegram-token}")
    public final String telegramToken = null;
    public final Map<Long, TrackList> usersWithTrackList;

    public ApplicationConfig() {
        this.usersWithTrackList = new HashMap<>();
    }

}
