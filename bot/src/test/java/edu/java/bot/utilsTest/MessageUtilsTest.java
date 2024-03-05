package edu.java.bot.utilsTest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import edu.java.bot.utils.MessageUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class MessageUtilsTest {
    @Test
    void getLinkTest() {
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.text()).thenReturn("/command 11");
        String result = MessageUtils.getLink(message, "/command");
        assertEquals("11", result);
    }

    @Test
    void getLinkWithTwoWordTest() {
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.text()).thenReturn("/command 11 11");
        String result = MessageUtils.getLink(message, "/command");
        assertEquals("11 11", result);
    }

    @Test
    void getUserIdTest() {
        Message message = Mockito.mock(Message.class);
        User user = Mockito.mock(User.class);
        Mockito.when(message.from()).thenReturn(user);
        Mockito.when(user.id()).thenReturn(1L);

        Long userId = MessageUtils.getUserId(message);
        assertEquals(Long.valueOf(1L), userId);
    }

    @Test
    void getChatIdTest() {
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(1L);

        Long chatId = MessageUtils.getChatId(message);
        assertEquals(Long.valueOf(1L), chatId);
    }

    @Test
    void getCommandTest() {
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.text()).thenReturn("/command 11 11");

        String command = MessageUtils.getCommand(message);
        assertEquals("/command", command);
    }
}
