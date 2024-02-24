package edu.java.clients.stackoverflow;

import edu.java.clients.stackoverflow.dto.StackoverflowResponse;
import reactor.core.publisher.Mono;

public interface StackoverflowClient {
    Mono<StackoverflowResponse> fetch(long id);

}
