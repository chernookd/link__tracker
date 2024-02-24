package edu.java.clients.github;

import edu.java.clients.github.dto.GithubResponse;
import reactor.core.publisher.Mono;

public interface GithubClient {
    Mono<GithubResponse> fetch(String owner, String repos);
}
