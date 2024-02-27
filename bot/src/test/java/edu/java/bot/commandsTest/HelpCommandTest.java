package edu.java.bot.commandsTest;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.core.MySendMessage;
import edu.java.bot.core.TrackList;
import edu.java.bot.core.commands.Command;
import edu.java.bot.core.commands.CommandBuilder;
import edu.java.bot.core.commands.HelpCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelpCommandTest {

    private CommandBuilder commandBuilder;
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

        commandBuilder = mock(CommandBuilder.class);
        when(commandBuilder.buldCommandsList()).thenReturn(Arrays.asList(command1, command2, command3));

        helpCommand = new HelpCommand(commandBuilder);
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

        String expectedMessage = "1 - description1\n2 - description2\n3 - description3\n";
        MySendMessage correct = new MySendMessage(1L, expectedMessage);

        assertEquals(correct.message(), result.message());
    }
}
