package edu.java.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatLink {
    private long id;
    private long chatId;
    private long linkId;
    //private OffsetDateTime createdAt;
}
