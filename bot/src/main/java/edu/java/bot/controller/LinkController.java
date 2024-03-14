package edu.java.bot.controller;

import edu.java.bot.controller.dto.LinkUpdateRequest;
import edu.java.bot.service.UpdateProcessor;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@Slf4j
public class LinkController {

    private final UpdateProcessor updateProcessor;

    @PostMapping(value = "/updates", produces = APPLICATION_JSON_VALUE)
    public void update(@RequestBody @Valid LinkUpdateRequest request) {
        updateProcessor.process(request);
        log.info(request.toString());
    }
}
