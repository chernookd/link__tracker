package edu.java.bot.commandsTest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import edu.java.bot.service.command.UntrackCommand;
import edu.java.bot.service.scrapperClientService.ScrapperClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class UntrackCommandTest {

    private UntrackCommand untrackCommand;
    private ScrapperClientService scrapperClientService;

    @BeforeEach
    public void setup() {
        scrapperClientService = Mockito.mock(ScrapperClientService.class);
        untrackCommand = new UntrackCommand(scrapperClientService);
    }

    @Test
    public void testHandleWithoutLinks() {
        String correctAnswer = "Прекратил отслеживание ссылки: https://github.com/chernookd/link__tracker/pull/1";
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);
        User userMock = Mockito.mock(User.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/untrack https://github.com/chernookd/link__tracker/pull/1");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);
        when(messageMock.from()).thenReturn(userMock);
        when(userMock.id()).thenReturn(1L);


        SendMessage result = untrackCommand.handle(updateMock);
        SendMessage correct = new SendMessage(1L, correctAnswer);

        assertTrue(correct.toWebhookResponse().equals(result.toWebhookResponse()));
    }

    @Test
    public void testHandleWithLinks() {
        String correctAnswer = "Ссылка неправильная";
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);
        User userMock = Mockito.mock(User.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/untrack ");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);
        when(messageMock.from()).thenReturn(userMock);
        when(userMock.id()).thenReturn(1L);


        SendMessage result = untrackCommand.handle(updateMock);
        SendMessage correct = new SendMessage(1L, correctAnswer);

        assertTrue(correct.toWebhookResponse().equals(result.toWebhookResponse()));
    }
}
