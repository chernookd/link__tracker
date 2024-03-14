package edu.java.bot.controller.dto;

import edu.java.bot.controller.exception.validator.ListIdPositive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkUpdateRequest {
    private @NotNull @Positive Long id;
    private @NotNull URI url;
    private @NotNull @NotBlank String description;
    private @NotNull @ListIdPositive List<Long> tgChatIds;
}

