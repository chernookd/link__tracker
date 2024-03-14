package edu.java.botClient.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkUpdateRequest {
    private Long id;
    private String url;
    private String description;
    private List<Long> tgChatIds;
}
