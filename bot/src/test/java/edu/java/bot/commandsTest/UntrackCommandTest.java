package edu.java.bot.commandsTest;

import edu.java.bot.configuration.ApplicationConfig;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.core.MySendMessage;
import edu.java.bot.core.TrackList;

import edu.java.bot.core.commands.UntrackCommand;
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
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testcontainers.shaded.com.google.common.base.CharMatcher.any;

public class UntrackCommandTest {

    private TrackList trackList;
    private UntrackCommand untrackCommand;

    @BeforeEach
    public void setup() {
        trackList = mock(TrackList.class);
        untrackCommand = new UntrackCommand(trackList);
    }

    @Test
    public void testHandleWithoutLinks() {
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

        String correctAnswer = "Зарегестрируйтесь /start";

        MySendMessage result = untrackCommand.handle(updateMock);

        assertEquals(correctAnswer.trim(), result.message().trim());
    }

    @Test
    public void testHandleWithLinks() {
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

        String correctAnswer = "Удалил ссылку https://github.com/chernookd/link__tracker/pull/1";

        MySendMessage result = untrackCommand.handle(updateMock);

        assertEquals(correctAnswer, result.message());
        assertFalse(links.contains("https://github.com/chernookd/link__tracker/pull/1"));
    }
}
