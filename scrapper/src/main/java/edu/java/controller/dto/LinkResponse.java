package edu.java.controller.dto;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class LinkResponse {
    private @NonNull Long id;
    private @NonNull URI url;
}
