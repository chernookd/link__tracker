package edu.java.bot.core.test;

import edu.java.bot.core.TrackList;
import edu.java.bot.core.UserMessageProcessorImpl;
import edu.java.bot.core.commands.Command;
import edu.java.bot.core.commands.CommandBuilder;
import edu.java.bot.core.commands.HelpCommand;
import edu.java.bot.core.commands.ListCommand;
import edu.java.bot.core.commands.StartCommand;
import edu.java.bot.core.commands.TrackCommand;
import edu.java.bot.core.commands.UntrackCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

public class UserMessageProcessorImplTest {

    private UserMessageProcessorImpl userMessageProcessor;

    @BeforeEach
    public void setUp() {
        CommandBuilder commandBuilderMock = Mockito.mock(CommandBuilder.class);

        Mockito.when(commandBuilderMock.buildCommand("/start")).thenReturn(new StartCommand(new TrackList()));
        Mockito.when(commandBuilderMock.buildCommand("/help")).thenReturn(new HelpCommand(commandBuilderMock));
        Mockito.when(commandBuilderMock.buildCommand("/track")).thenReturn(new TrackCommand(new TrackList()));
        Mockito.when(commandBuilderMock.buildCommand("/untrack")).thenReturn(new UntrackCommand(new TrackList()));
        Mockito.when(commandBuilderMock.buildCommand("/list")).thenReturn(new ListCommand(new TrackList()));

        userMessageProcessor = new UserMessageProcessorImpl(commandBuilderMock);
    }

    @Test
    public void testProcess() {
        Command command = userMessageProcessor.process("/start");
        System.out.println(command);
        assertTrue(command instanceof StartCommand);

        command = userMessageProcessor.process("/help");
        assertTrue(command instanceof HelpCommand);

        command = userMessageProcessor.process("/track");
        assertTrue(command instanceof TrackCommand);

        command = userMessageProcessor.process("/untrack");
        assertTrue(command instanceof UntrackCommand);

        command = userMessageProcessor.process("/list");
        assertTrue(command instanceof ListCommand);
    }
}
