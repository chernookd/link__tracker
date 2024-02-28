package edu.java.clients.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class GithubResponse {

    private long id;

    private String name;

    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;

    private GithubOwner owner;

}
