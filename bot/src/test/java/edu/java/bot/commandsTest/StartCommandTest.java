package edu.java.bot.commandsTest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.core.MySendMessage;
import edu.java.bot.core.TrackList;

import edu.java.bot.core.commands.StartCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StartCommandTest {

    private ApplicationConfig applicationConfig;
    private StartCommand startCommand;

    @BeforeEach
    public void setup() {
        applicationConfig = mock(ApplicationConfig.class);
        startCommand = new StartCommand(applicationConfig);
    }

    @Test
    public void testHandleWithoutLinks() {
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

        Map<Long, TrackList> usersWithTrackList = new HashMap<>();
        when(applicationConfig.getUsersWithTrackList()).thenReturn(usersWithTrackList);

        String correctAnswer = "Отслеживание началось";

        MySendMessage result = startCommand.handle(updateMock);

        assertEquals(result.message().trim(), correctAnswer.trim());
    }

    @Test
    public void testHandleWithLinks() {
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

        Map<Long, TrackList> usersWithTrackList = new HashMap<>();
        usersWithTrackList.put(1L, new TrackList(Set.of("link1")));
        when(applicationConfig.getUsersWithTrackList()).thenReturn(usersWithTrackList);

        String correctAnswer = "Аккаунт найден\n" +
            "Прошлые ссылки : \n" +
            "link1\n";

        MySendMessage result = startCommand.handle(updateMock);

        assertEquals(result.message().trim(), correctAnswer.trim());
    }
}
