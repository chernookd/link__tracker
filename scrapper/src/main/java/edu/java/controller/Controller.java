package edu.java.controller;

import edu.java.controller.dto.AddLinkRequest;
import edu.java.controller.dto.LinkResponse;
import edu.java.controller.dto.ListLinksResponse;
import edu.java.controller.dto.RemoveLinkRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final CommandProcessor commandProcessor;

    @PostMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addChat(@PathVariable @Positive @Valid Long id) {
        commandProcessor.addChat(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteChat(@PathVariable @Positive @Valid Long id) {
        commandProcessor.deleteChat(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/links", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ListLinksResponse> getAllTrackingLinks() {
        ListLinksResponse links = commandProcessor.getAllTrackingLinks();

        return ResponseEntity.ok().body(links);
    }

    @PostMapping(value = "/links", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LinkResponse> addTrackLink(@RequestBody @Valid AddLinkRequest request,
        @Positive Long chatId) {
        LinkResponse linkResponse = commandProcessor.addTrackLink(request, chatId);

        return ResponseEntity.ok().body(linkResponse);
    }

    @DeleteMapping(value = "/links", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LinkResponse> deleteTrackLink(@RequestBody @Valid RemoveLinkRequest request,
        @Positive Long chatId) {
        LinkResponse linkResponse = commandProcessor.deleteTrackLink(request, chatId);

        return ResponseEntity.ok().body(linkResponse);
    }
}
