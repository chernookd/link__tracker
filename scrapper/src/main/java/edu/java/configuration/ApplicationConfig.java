package edu.java.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull

    @Bean
    Scheduler scheduler,
    BaseUrls url
) {
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration delay) {
    }

    public record BaseUrls(String gitHubBaseUrl, String stackOverflowBaseUrl, String botBaseUrl) {
    }
}
