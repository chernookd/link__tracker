package edu.java.clients.stackoverflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StackoverflowItemOwner {

    @JsonProperty("account_id")
    private long accountId;

    private int reputation;

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("display_name")
    private String displayName;
}
