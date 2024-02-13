package edu.java.bot.commandsTest;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.core.commands.TrackCommand;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.core.MySendMessage;
import edu.java.bot.core.TrackList;

import edu.java.bot.core.commands.UntrackCommand;
import edu.java.bot.utils.Utils;
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

public class UntrackCommandTest {

    private ApplicationConfig applicationConfig;
    private UntrackCommand untrackCommand;

    @BeforeEach
    public void setup() {
        applicationConfig = mock(ApplicationConfig.class);
        untrackCommand = new UntrackCommand(applicationConfig);
    }

    @Test
    public void testHandleWithoutLinks() {
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);
        User userMock = Mockito.mock(User.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/untrack link");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);
        when(messageMock.from()).thenReturn(userMock);
        when(userMock.id()).thenReturn(1L);

        Map<Long, TrackList> usersWithTrackList = new HashMap<>();
        when(applicationConfig.getUsersWithTrackList()).thenReturn(usersWithTrackList);

        String correctAnswer = "Зарегестрируйтесь /start";

        MySendMessage result = untrackCommand.handle(updateMock);

        assertEquals(result.message().trim(), correctAnswer.trim());
    }

    @Test
    public void testHandleWithValidLink() {
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);
        User userMock = Mockito.mock(User.class);
        Utils utils = Mockito.mock(Utils.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/untrack https://habr.com/ru/articles/172239/");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);
        when(messageMock.from()).thenReturn(userMock);
        when(userMock.id()).thenReturn(1L);
        when(utils.isValidLink("/untrack https://habr.com/ru/articles/172239/")).thenReturn(true);

        Map<Long, TrackList> usersWithTrackList = new HashMap<>();
        usersWithTrackList.put(1L, new TrackList(new HashSet<>(Arrays.asList("link1", "link2", "https://habr.com/ru/articles/172239/"))));
        when(applicationConfig.getUsersWithTrackList()).thenReturn(usersWithTrackList);


        String correctAnswer = "Удалил ссылку https://habr.com/ru/articles/172239/";

        MySendMessage result = untrackCommand.handle(updateMock);

        assertEquals(result.message().trim(), correctAnswer.trim());
    }

    @Test
    public void testHandleWithInvalidLink() {
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);
        User userMock = Mockito.mock(User.class);
        Utils utils = Mockito.mock(Utils.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/untrack link");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);
        when(messageMock.from()).thenReturn(userMock);
        when(userMock.id()).thenReturn(1L);

        Map<Long, TrackList> usersWithTrackList = new HashMap<>();
        usersWithTrackList.put(1L, new TrackList(Set.of("link")));
        when(applicationConfig.getUsersWithTrackList()).thenReturn(usersWithTrackList);
        when(utils.isValidLink("link")).thenReturn(false);


        MySendMessage result = untrackCommand.handle(updateMock);
        String correctResult = "Некорректная ссылка link";

        assertEquals(result.message(), correctResult);

    }
}
