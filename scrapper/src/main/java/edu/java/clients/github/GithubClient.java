package edu.java.clients.github;

import edu.java.clients.github.dto.GithubResponse;

public interface GithubClient {
    GithubResponse fetch(String owner, String repos);
}
