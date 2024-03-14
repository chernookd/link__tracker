package edu.java.bot.scrapperClient.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ListLinksResponse {
    private @NonNull List<LinkResponse> links;
    private @NonNull Integer size;
}
