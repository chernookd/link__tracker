package edu.java.clients.stackoverflow;

import edu.java.clients.stackoverflow.dto.StackoverflowResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class StackoverflowClientImpl implements StackoverflowClient {

    private final WebClient stackOverflowWebClient;

    public StackoverflowClientImpl(WebClient stackOverflowWebClient) {
        this.stackOverflowWebClient = stackOverflowWebClient;
    }


    @Override
    public StackoverflowResponse fetch(long id) {
        StackoverflowResponse stackoverflowResponse = stackOverflowWebClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/{id}")
                .queryParam("order", "desc")
                .queryParam("sort", "activity")
                .queryParam("site", "stackoverflow")
                .build(id)).retrieve()
            .bodyToMono(StackoverflowResponse.class)
            .block();

        return stackoverflowResponse;
    }
}
