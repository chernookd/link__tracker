package edu.java.controller;

import edu.java.controller.dto.AddLinkRequest;
import edu.java.controller.dto.LinkResponse;
import edu.java.controller.dto.ListLinksResponse;
import edu.java.controller.dto.RemoveLinkRequest;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommandProcessor {
    public void addChat(Long id) {
        log.info("add chat id" + id);
    }

    public void deleteChat(Long id) {
        log.info("delete chat id" + id);
    }

    public ListLinksResponse getAllTrackingLinks() {
        ListLinksResponse links;
        try {
            links =
                new ListLinksResponse(
                    new ArrayList<>(List.of(
                        new LinkResponse(
                            1L,
                            new URI("http://www.example.com")
                        ))), 1);
        } catch (Exception e) {
            throw new RuntimeException("invalid url");
        }
        return links;
    }

    public LinkResponse addTrackLink(AddLinkRequest request, @Positive Long id) {
        log.info("add track link" + request.getLink());

        return new LinkResponse(id, request.getLink());
    }

    public LinkResponse deleteTrackLink(RemoveLinkRequest request, @Positive Long id) {
        log.info("delete track link" + request.getLink());

        return new LinkResponse(id, request.getLink());
    }
}
