package edu.java.clients.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GithubOwner {

    @JsonProperty("login")
    private String login;

    @JsonProperty("id")
    private long id;
}
