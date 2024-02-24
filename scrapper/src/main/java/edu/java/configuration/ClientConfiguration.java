package edu.java.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {

    private final ApplicationConfig applicationConfig;

    @Bean
    public WebClient gitHubWebClient() {
        String gitHubBaseUrl = applicationConfig.urls().gitHubBaseUrl();

        WebClient webClient = WebClient.builder()
            .baseUrl(gitHubBaseUrl)
            .build();
        return webClient;
    }

    @Bean
    public WebClient stackOverflowWebClient() {
        String stackOverFlowBaseUrl = applicationConfig.urls().gitHubBaseUrl();

        WebClient webClient = WebClient
            .builder()
            .baseUrl(stackOverFlowBaseUrl)
            .build();
        return webClient;
    }


}
