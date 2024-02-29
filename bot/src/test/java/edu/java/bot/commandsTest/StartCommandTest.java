package edu.java.bot.commandsTest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.InMemoryTrackRepository;

import edu.java.bot.service.command.StartCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StartCommandTest {

    private InMemoryTrackRepository trackList;
    private StartCommand startCommand;

    @BeforeEach
    public void setup() {
        trackList = mock(InMemoryTrackRepository.class);
        startCommand = new StartCommand(trackList);
    }

    @Test
    public void testHandleWithoutLinks() {
        String correctAnswer = "Отслеживание началось";
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

        Map<Long, Set<String>> usersWithLinks = new HashMap<>();
        when(trackList.getUsersWithLinks()).thenReturn(usersWithLinks);


        SendMessage result = startCommand.handle(updateMock);
        SendMessage correct = new SendMessage(1L, correctAnswer);

        assertTrue(correct.toWebhookResponse().equals(result.toWebhookResponse()));
    }

    @Test
    public void testHandleWithLinks() {
        String correctAnswer = "Аккаунт найден\nПрошлые ссылки : \nlink1\n";
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

        Map<Long, Set<String>> usersWithLinks = new HashMap<>();
        usersWithLinks.put(1L, Set.of("link1"));
        when(trackList.getUsersWithLinks()).thenReturn(usersWithLinks);

        SendMessage result = startCommand.handle(updateMock);
        SendMessage correct = new SendMessage(1L, correctAnswer);

        assertTrue(correct.toWebhookResponse().equals(result.toWebhookResponse()));
    }
}
