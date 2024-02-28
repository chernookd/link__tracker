package edu.java.clients.github.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GithubOwner {

    private String login;

    private long id;
}
