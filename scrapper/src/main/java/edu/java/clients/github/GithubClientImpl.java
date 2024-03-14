package edu.java.clients.github;

import edu.java.clients.github.dto.GithubResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class GithubClientImpl implements GithubClient {

    private final WebClient gitHubWebClient;

    public GithubClientImpl(WebClient gitHubWebClient) {
        this.gitHubWebClient = gitHubWebClient;
    }

    public GithubResponse fetch(String owner, String repos) {
        GithubResponse githubResponse = gitHubWebClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/{owner}/{repos}")
                .build(owner, repos))
            .retrieve()
            .bodyToMono(GithubResponse.class)
            .block();

        return githubResponse;
    }
}
