package edu.java.botClient;

import edu.java.botClient.dto.ApiErrorResponse;
import edu.java.botClient.dto.LinkUpdateRequest;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class BotClient {

    private final WebClient botClient;

    public BotClient(WebClient botWebClient) {
        this.botClient = botWebClient;
    }

    public Mono<ApiErrorResponse> updateLink(Long id, String url, String description, List<Long> tgChatIds) {
        LinkUpdateRequest request = new LinkUpdateRequest(id, url, description, tgChatIds);

        return botClient.post()
            .uri("/updates")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .bodyToMono(ApiErrorResponse.class);
    }
}
