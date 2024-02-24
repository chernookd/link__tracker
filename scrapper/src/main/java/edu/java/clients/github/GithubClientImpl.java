package edu.java.clients.github;

import edu.java.clients.github.dto.GithubResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
public class GithubClientImpl implements GithubClient {

    private final WebClient webClient;

    @Autowired
    public GithubClientImpl(@Qualifier("gitHubWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<GithubResponse> fetch(String owner, String repos) {
        Mono<GithubResponse> githubResponseMono = webClient.get()
            .uri(owner + "/" + repos)
            .retrieve()
            .bodyToMono(GithubResponse.class);

        return githubResponseMono;
    }

}
