package edu.java.bot.commandsTest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.command.Command;
import edu.java.bot.service.command.HelpCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelpCommandTest {

    private HelpCommand helpCommand;

    @BeforeEach
    public void setup() {
        Command command1 = mock(Command.class);
        Command command2 = mock(Command.class);
        Command command3 = mock(Command.class);

        when(command1.command()).thenReturn("1");
        when(command1.description()).thenReturn("description1");
        when(command2.command()).thenReturn("2");
        when(command2.description()).thenReturn("description2");
        when(command3.command()).thenReturn("3");
        when(command3.description()).thenReturn("description3");

        Command[] commands = {command1, command2, command3};

        helpCommand = new HelpCommand(commands);
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

        SendMessage result = helpCommand.handle(updateMock);

        String expectedMessage = "1 - description1\n2 - description2\n3 - description3\n";
        SendMessage correct = new SendMessage(1L, expectedMessage);


        assertTrue(correct.toWebhookResponse().equals(result.toWebhookResponse()));
    }
}
