package edu.java.bot.commandsTest;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.core.commands.TrackCommand;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.core.MySendMessage;
import edu.java.bot.core.TrackList;

import edu.java.bot.utils.LinkValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrackCommandTest {

    private TrackList trackList;
    private TrackCommand trackCommand;

    @BeforeEach
    public void setup() {
        trackList = mock(TrackList.class);
        trackCommand = new TrackCommand(trackList);
    }

    @Test
    public void testHandleWithoutLinks() {
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);
        User userMock = Mockito.mock(User.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/track https://github.com/chernookd/link__tracker/pull/1");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);
        when(messageMock.from()).thenReturn(userMock);
        when(userMock.id()).thenReturn(1L);

        Map<Long, Set<String>> usersWithLinks = new HashMap<>();
        when(trackList.getUsersWithLinks()).thenReturn(usersWithLinks);

        String correctAnswer = "Зарегестрируйтесь /start";

        MySendMessage result = trackCommand.handle(updateMock);

        assertEquals(correctAnswer.trim(), result.message().trim());
    }

    @Test
    public void testHandleWithLinks() {
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);
        User userMock = Mockito.mock(User.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/track https://github.com/chernookd/link__tracker/pull/1");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);
        when(messageMock.from()).thenReturn(userMock);
        when(userMock.id()).thenReturn(1L);

        Map<Long, Set<String>> usersWithLinks = new HashMap<>();
        usersWithLinks.put(1L, new HashSet<>());
        when(trackList.getUsersWithLinks()).thenReturn(usersWithLinks);

        String correctAnswer = "Начал отслеживание ссылки: https://github.com/chernookd/link__tracker/pull/1";

        MySendMessage result = trackCommand.handle(updateMock);

        assertEquals(correctAnswer.trim(), result.message().trim());
    }
}
