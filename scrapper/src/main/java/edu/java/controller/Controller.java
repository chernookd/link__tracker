package edu.java.controller;

import edu.java.controller.dto.AddLinkRequest;
import edu.java.controller.dto.LinkResponse;
import edu.java.controller.dto.ListLinksResponse;
import edu.java.controller.dto.RemoveLinkRequest;
import edu.java.controller.exception.CustomExceptionHandler;
import edu.java.controller.exception.FindLinkException;
import edu.java.domain.dto.Link;
import edu.java.service.serviceDao.LinkService;
import edu.java.service.serviceDao.TgChatService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CustomExceptionHandler
public class Controller {

    private final TgChatService tgChatService;

    private final LinkService linkService;

    @PostMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public void addChat(@PathVariable @Positive @Valid Long id) {
        tgChatService.register(id);
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public void deleteChat(@PathVariable @Positive @Valid Long id) {
        tgChatService.unregister(id);
    }

    @GetMapping(value = "/links", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ListLinksResponse> getAllTrackingLinks(@RequestParam("id") Long id) throws FindLinkException {
        if (id == null) {
            throw new FindLinkException();
        }

        List<Link> linkList = (List<Link>) linkService.listAll(id);
        if (linkList.isEmpty()) {
            throw new FindLinkException();
        }

        List<LinkResponse> linkResponseList = linkList.stream()
            .map(link -> {
                return new LinkResponse(link.getId(), link.getUrl());
            }).toList();

        return ResponseEntity.ok().body(new ListLinksResponse(linkResponseList, linkResponseList.size()));
    }


    @PostMapping(value = "/links", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LinkResponse> addTrackLink(@RequestBody @Valid AddLinkRequest request,
        @Positive @PathVariable Long chatId) {
        Link link = linkService.add(chatId, request.getLink());

        return ResponseEntity.ok().body(new LinkResponse(link.getId(), link.getUrl()));
    }

    @DeleteMapping(value = "/links", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LinkResponse> deleteTrackLink(@RequestBody @Valid RemoveLinkRequest request,
        @Positive @PathVariable Long chatId) throws Exception {
        Link link = linkService.remove(chatId, request.getLink());

        return ResponseEntity.ok().body(new LinkResponse(link.getId(), link.getUrl()));
    }
}
