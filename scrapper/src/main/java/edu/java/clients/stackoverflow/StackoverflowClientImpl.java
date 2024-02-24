package edu.java.clients.stackoverflow;

import edu.java.clients.stackoverflow.dto.StackoverflowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class StackoverflowClientImpl implements StackoverflowClient {

    private final WebClient webClient;

    @Autowired
    public StackoverflowClientImpl(@Qualifier("stackOverflowWebClient") WebClient webClient) {
        this.webClient = webClient;
    }


    @Override
    public Mono<StackoverflowResponse> fetch(long id) {
        Mono<StackoverflowResponse> stackoverflowResponseMono = webClient.get()
            .uri(id + "?order=desc&sort=activity&site=stackoverflow")
            .retrieve()
            .bodyToMono(StackoverflowResponse.class);

        return stackoverflowResponseMono;
    }
}
