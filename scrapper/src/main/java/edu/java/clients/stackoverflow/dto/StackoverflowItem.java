package edu.java.clients.stackoverflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class StackoverflowItem {

    private StackoverflowItemOwner owner;

    @JsonProperty("is_answered")
    private boolean isAnswered;

    @JsonProperty("view_count")
    private int viewCount;

    @JsonProperty("answer_count")
    private int answerCount;

    private int score;

    @JsonProperty("creation_date")
    private OffsetDateTime creationDate;

    @JsonProperty("question_id")
    private long questionId;

    @JsonProperty("last_activity_date")
    private OffsetDateTime lastEditDate;

}
