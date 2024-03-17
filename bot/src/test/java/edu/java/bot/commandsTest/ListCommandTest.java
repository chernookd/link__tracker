package edu.java.bot.commandsTest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.scrapperClient.dto.LinkResponse;
import edu.java.bot.scrapperClient.dto.ListLinksResponse;
import edu.java.bot.service.command.ListCommand;
import edu.java.bot.service.scrapperClientService.ScrapperClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ListCommandTest {

    private ListCommand listCommand;
    private ScrapperClientService scrapperClientService;

    @BeforeEach
    public void setup() {
        scrapperClientService = Mockito.mock(ScrapperClientService.class);
        listCommand = new ListCommand(scrapperClientService);
    }

    @Test
    public void testHandleWithoutLinks() {
        String correctAnswer = "Ccылки :\n";
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);
        User userMock = Mockito.mock(User.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/list");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);
        when(messageMock.from()).thenReturn(userMock);
        when(userMock.id()).thenReturn(1L);

        when(scrapperClientService.getLinks(1L)).thenReturn(new ListLinksResponse(Collections.emptyList(), 0));

        SendMessage result = listCommand.handle(updateMock);
        SendMessage correct = new SendMessage(1L, correctAnswer);

        assertEquals(correct.toWebhookResponse(), result.toWebhookResponse());
    }

    @Test
    public void testHandleWithLinks() {
        String correctAnswer = "Ccылки :\nhttps://www.baeldung.com/spring-dirtiescontext\n";
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);
        User userMock = Mockito.mock(User.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/list");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);
        when(messageMock.from()).thenReturn(userMock);
        when(userMock.id()).thenReturn(1L);

        LinkResponse linkResponse = new LinkResponse(1L, URI.create("https://www.baeldung.com/spring-dirtiescontext"));
        linkResponse.setUrl(URI.create("https://www.baeldung.com/spring-dirtiescontext"));
        ListLinksResponse listLinksResponse = new ListLinksResponse(List.of(linkResponse), 1);

        when(scrapperClientService.getLinks(1L)).thenReturn(listLinksResponse);

        SendMessage result = listCommand.handle(updateMock);
        SendMessage correct = new SendMessage(1L, correctAnswer);

        assertEquals(correct.toWebhookResponse(), result.toWebhookResponse());
    }
}
