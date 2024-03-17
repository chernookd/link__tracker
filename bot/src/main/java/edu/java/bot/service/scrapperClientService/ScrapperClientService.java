package edu.java.bot.service.scrapperClientService;

import edu.java.bot.scrapperClient.ScrapperClient;
import edu.java.bot.scrapperClient.dto.AddLinkRequest;
import edu.java.bot.scrapperClient.dto.ListLinksResponse;
import edu.java.bot.scrapperClient.dto.RemoveLinkRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ScrapperClientService {

    private final ScrapperClient scrapperClient;


    public void register(Long chatId) {
        scrapperClient.addChat(chatId);
    }

    public void unregister(Long chatId) {
        scrapperClient.deleteChat(chatId);
    }

    public void addLink(Long chatId, URI url) {
        scrapperClient.addTrackLink(chatId, new AddLinkRequest(url));
    }

    public void removeLink(Long chatId, URI url) {
        scrapperClient.deleteTrackLink(chatId, new RemoveLinkRequest(url));
    }

    public ListLinksResponse getLinks(Long chatId) {

        Mono<ListLinksResponse> listLinksResponseMono = scrapperClient.getAllTrackingLinks(chatId);
        return listLinksResponseMono.block();
    }

}
