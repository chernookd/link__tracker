package edu.java.bot.commandsTest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.InMemoryTrackRepository;
import edu.java.bot.service.command.ListCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListCommandTest {

    private InMemoryTrackRepository trackList;
    private ListCommand listCommand;

    @BeforeEach
    public void setup() {
        trackList = mock(InMemoryTrackRepository.class);
        listCommand = new ListCommand(trackList);
    }

    @Test
    public void testHandleWithoutLinks() {
        String correctAnswer = "Зарегестрируйтесь /start";
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

        Map<Long, Set<String>> usersWithLinks = new HashMap<>();
        when(trackList.checkingForEmptiness()).thenReturn(true);

        SendMessage result = listCommand.handle(updateMock);
        SendMessage correct = new SendMessage(1L, correctAnswer);

        assertTrue(correct.toWebhookResponse().equals(result.toWebhookResponse()));
    }

    @Test
    public void testHandleWithLinks() {
        String correctAnswer = "Ccылки :\nlink1\n";
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

        when(trackList.checkingForEmptiness()).thenReturn(false);
        when(trackList.containsKey(1L)).thenReturn(true);
        when(trackList.getByUserId(1L)).thenReturn(Set.of("link1"));

        SendMessage result = listCommand.handle(updateMock);
        SendMessage correct = new SendMessage(1L, correctAnswer);

        assertTrue(correct.toWebhookResponse().equals(result.toWebhookResponse()));
    }
}
