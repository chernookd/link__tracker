package edu.java.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class BotClientConfig {

    private final ApplicationConfig applicationConfig;

    @Bean
    public WebClient botWebClient() {
        String botBaseUrl = applicationConfig.urls().botBaseUrl();

        WebClient webClient = WebClient.builder()
            .baseUrl(botBaseUrl)
            .build();
        return webClient;
    }

}
