package edu.java.bot.commandsTest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.core.MySendMessage;
import edu.java.bot.core.TrackList;
import edu.java.bot.core.commands.HelpCommand;
import edu.java.bot.core.commands.ListCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListCommandTest {

    private TrackList trackList;
    private ListCommand listCommand;

    @BeforeEach
    public void setup() {
        trackList = mock(TrackList.class);
        listCommand = new ListCommand(trackList);
    }

    @Test
    public void testHandleWithoutLinks() {
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
        when(trackList.getUsersWithLinks()).thenReturn(usersWithLinks);

        String correctAnswer = "Зарегестрируйтесь /start";

        MySendMessage result = listCommand.handle(updateMock);

        assertEquals(correctAnswer.trim(), result.message().trim());
    }

    @Test
    public void testHandleWithLinks() {
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
        usersWithLinks.put(1L, Set.of("link1"));
        when(trackList.getUsersWithLinks()).thenReturn(usersWithLinks);

        String correctAnswer = "Ccылки :\nlink1";

        MySendMessage result = listCommand.handle(updateMock);

        assertEquals(correctAnswer.trim(), result.message().trim());
    }
}
