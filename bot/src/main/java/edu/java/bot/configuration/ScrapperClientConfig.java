package edu.java.bot.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class ScrapperClientConfig {

    private final ApplicationConfig applicationConfig;

    @Bean
    public WebClient scrapperWebClient() {
        String botBaseUrl = applicationConfig.urls().scrapperBaseUrl();

        WebClient webClient = WebClient.builder()
            .baseUrl(botBaseUrl)
            .build();
        return webClient;
    }

}
