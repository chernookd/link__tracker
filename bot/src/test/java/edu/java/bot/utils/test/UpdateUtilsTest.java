package edu.java.bot.utils.test;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.utils.UpdateUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateUtilsTest {
    @Test
    void getChatIdTest() {
        Update update = mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);

        Long chatId = UpdateUtils.getChatId(update);

        assertEquals(Long.valueOf(1L), chatId);
    }

    @Test
    void isValidUpdateTest() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("text");

        boolean isValid = UpdateUtils.isValidUpdate(update);

        assertTrue(isValid);
    }
}
