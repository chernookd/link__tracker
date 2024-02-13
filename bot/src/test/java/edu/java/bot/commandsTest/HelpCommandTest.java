package edu.java.bot.commandsTest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.core.MySendMessage;
import edu.java.bot.core.TrackList;
import edu.java.bot.core.commands.HelpCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelpCommandTest {

    private ApplicationConfig applicationConfig;
    private HelpCommand helpCommand;

    @BeforeEach
    public void setup() {
        applicationConfig = mock(ApplicationConfig.class);
        helpCommand = new HelpCommand(applicationConfig);
    }



    @Test
    public void testHandle() {
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/help");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);


        MySendMessage result = helpCommand.handle(updateMock);

        MySendMessage coorect = new MySendMessage(1L, "/help - Выводит список доступных команд\n" +
            "/list - Выводит список отслеживаемых ссылок\n" +
            "\n" +
            "/start - Регестрация пользователя\n" +
            "\n" +
            "/untrack -  'ссылка' - удаляет заданную ссылку из списка отслеживаемых\n" +
            "\n" +
            "/track -  'ссылка' - отслеживает заданную ссылку");

        assertEquals(coorect.message().trim(), result.message().trim());
    }




}
