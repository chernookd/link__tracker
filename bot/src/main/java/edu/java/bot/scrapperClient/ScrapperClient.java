package edu.java.bot.scrapperClient;

import edu.java.bot.controller.dto.ApiErrorResponse;
import edu.java.bot.controller.exception.ApiException;
import edu.java.bot.scrapperClient.dto.AddLinkRequest;
import edu.java.bot.scrapperClient.dto.LinkResponse;
import edu.java.bot.scrapperClient.dto.ListLinksResponse;
import edu.java.bot.scrapperClient.dto.RemoveLinkRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
@SuppressWarnings("MultipleStringLiterals")
public class ScrapperClient {

    private final WebClient scrapperWebClient;

    private static final String LINK = "/links";
    private static final String HEADER = "Tg-Chat-Id";

    public ScrapperClient(WebClient scrapperWebClient) {
        this.scrapperWebClient = scrapperWebClient;
    }

    public Mono<Void> addChat(Long id) {
        return scrapperWebClient.post()
            .uri("/{id}", id)
            .retrieve()
            .onStatus(
                HttpStatus.NOT_ACCEPTABLE::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).flatMap(error -> Mono.error(
                    new ApiException(error)))
              )
            .bodyToMono(Void.class);
    }


    public Mono<Void> deleteChat(Long id) {
        return scrapperWebClient.delete()
            .uri("/{id}", id)
            .retrieve()
            .onStatus(
                HttpStatus.NOT_ACCEPTABLE::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).flatMap(error -> Mono.error(
                    new ApiException(error)))
            )
            .bodyToMono(Void.class);
    }

    public Mono<ListLinksResponse> getAllTrackingLinks(Long id) {
        return scrapperWebClient.get()
            .uri(uriBuilder -> uriBuilder.path("/links").queryParam("id", id).build())
            .retrieve()
            .onStatus(HttpStatus.BAD_REQUEST::equals, response ->
                response.bodyToMono(ApiErrorResponse.class)
                    .flatMap(error -> Mono.error(new ApiException(error))))
            .onStatus(HttpStatus.NOT_ACCEPTABLE::equals, response ->
                response.bodyToMono(ApiErrorResponse.class)
                    .flatMap(error -> Mono.error(new ApiException(error))))
            .bodyToMono(ListLinksResponse.class)
            .onErrorResume(ApiException.class, error -> {
                return Mono.empty();
            });
    }


    public Mono<LinkResponse> addTrackLink(Long chatId, AddLinkRequest request) {
        return scrapperWebClient.post()
            .uri(LINK)
            .header(HEADER, String.valueOf(chatId))
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(ApiErrorResponse.class)
                    .flatMap(error -> Mono.error(new ApiException(error))))
            .onStatus(
                HttpStatus.NOT_ACCEPTABLE::equals,
                response -> response.bodyToMono(ApiErrorResponse.class)
                    .flatMap(error -> Mono.error(new ApiException(error))))
            .bodyToMono(LinkResponse.class);
    }

    public Mono<LinkResponse> deleteTrackLink(Long chatId, RemoveLinkRequest request) {
        return scrapperWebClient.delete()
            .uri(uriBuilder -> uriBuilder
                .path("/links/{id}")
                .build(request.getLink()))
            .header(HEADER, String.valueOf(chatId))
            .retrieve()
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).flatMap(error -> Mono.error(
                    new ApiException(error)))
            )
            .onStatus(
                HttpStatus.NOT_ACCEPTABLE::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).flatMap(error -> Mono.error(
                    new ApiException(error)))
            )
            .bodyToMono(LinkResponse.class);
    }
}
