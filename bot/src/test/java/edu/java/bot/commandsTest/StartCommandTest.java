package edu.java.bot.commandsTest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import edu.java.bot.service.command.StartCommand;
import edu.java.bot.service.scrapperClientService.ScrapperClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class StartCommandTest {

    private StartCommand startCommand;
    private ScrapperClientService scrapperClientService;


    @BeforeEach
    public void setup() {
        scrapperClientService = Mockito.mock(ScrapperClientService.class);
        startCommand = new StartCommand(scrapperClientService);
    }

    @Test
    public void testHandleWithoutLinks() {
        String correctAnswer = "Пользователь зарегестрирован";
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);
        User userMock = Mockito.mock(User.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/start");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);
        when(messageMock.from()).thenReturn(userMock);
        when(userMock.id()).thenReturn(1L);
        Mockito.doNothing().when(scrapperClientService).register(1L);


        SendMessage result = startCommand.handle(updateMock);
        SendMessage correct = new SendMessage(1L, correctAnswer);

        assertTrue(correct.toWebhookResponse().equals(result.toWebhookResponse()));
    }
}
