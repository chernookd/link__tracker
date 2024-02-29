package edu.java.bot.commandsTest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.InMemoryTrackRepository;

import edu.java.bot.service.command.UntrackCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UntrackCommandTest {
    private InMemoryTrackRepository trackList;
    private UntrackCommand untrackCommand;

    @BeforeEach
    public void setup() {
        trackList = mock(InMemoryTrackRepository.class);
        untrackCommand = new UntrackCommand(trackList);
    }

    @Test
    public void testHandleWithoutLinks() {
        String correctAnswer = "Зарегестрируйтесь /start";
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

        Map<Long, Set<String>> usersWithLinks = new HashMap<>();
        when(trackList.getUsersWithLinks()).thenReturn(usersWithLinks);


        SendMessage result = untrackCommand.handle(updateMock);
        SendMessage correct = new SendMessage(1L, correctAnswer);

        assertTrue(correct.toWebhookResponse().equals(result.toWebhookResponse()));
    }

    @Test
    public void testHandleWithLinks() {
        String correctAnswer = "Удалил ссылку https://github.com/chernookd/link__tracker/pull/1";
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

        Set<String> links = new HashSet<>(Set.of("https://github.com/chernookd/link__tracker/pull/1"));
        Map<Long, Set<String>> usersWithLinks = new HashMap<>(Map.of(1L, links));
        when(trackList.getUsersWithLinks()).thenReturn(usersWithLinks);

        SendMessage result = untrackCommand.handle(updateMock);
        SendMessage correct = new SendMessage(1L, correctAnswer);

        assertTrue(correct.toWebhookResponse().equals(result.toWebhookResponse()));
        assertFalse(links.contains("https://github.com/chernookd/link__tracker/pull/1"));
    }
}
