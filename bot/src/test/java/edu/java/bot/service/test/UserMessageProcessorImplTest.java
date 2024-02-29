package edu.java.bot.service.test;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.UserMessageProcessorImpl;
import edu.java.bot.service.command.Command;
import edu.java.bot.service.command.HelpCommand;
import edu.java.bot.service.command.ListCommand;
import edu.java.bot.service.command.StartCommand;
import edu.java.bot.service.command.TrackCommand;
import edu.java.bot.service.command.UntrackCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

public class UserMessageProcessorImplTest {

    private UserMessageProcessorImpl userMessageProcessor;
    private Command startCommand;
    private Command helpCommand;
    private Command listCommand;
    private Command trackCommand;
    private Command untrackCommand;


    @BeforeEach
    public void setUp() {
        startCommand = Mockito.mock(StartCommand.class);
        helpCommand = Mockito.mock(HelpCommand.class);
        listCommand = Mockito.mock(ListCommand.class);
        trackCommand = Mockito.mock(TrackCommand.class);
        untrackCommand = Mockito.mock(UntrackCommand.class);


        Command[] commands = {startCommand, helpCommand, listCommand, trackCommand, untrackCommand};

        userMessageProcessor = new UserMessageProcessorImpl(commands);
    }

    @Test
    public void startCommandTest() {
        Update update = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        Mockito.when(update.message()).thenReturn(mockMessage);
        Mockito.when(mockMessage.text()).thenReturn("/start");
        Mockito.when(startCommand.supports(update)).thenReturn(true);

        Command command = userMessageProcessor.process(update);
        assertTrue(command instanceof StartCommand);
    }

    @Test
    public void listCommandTest() {
        Update update = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        Mockito.when(update.message()).thenReturn(mockMessage);
        Mockito.when(mockMessage.text()).thenReturn("/list");
        Mockito.when(listCommand.supports(update)).thenReturn(true);

        Command command = userMessageProcessor.process(update);
        assertTrue(command instanceof ListCommand);
    }

    @Test
    public void helpCommandTest() {
        Update update = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        Mockito.when(update.message()).thenReturn(mockMessage);
        Mockito.when(mockMessage.text()).thenReturn("/help");
        Mockito.when(helpCommand.supports(update)).thenReturn(true);

        Command command = userMessageProcessor.process(update);
        assertTrue(command instanceof HelpCommand);
    }

    @Test
    public void trackCommandTest() {
        Update update = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        Mockito.when(update.message()).thenReturn(mockMessage);
        Mockito.when(mockMessage.text()).thenReturn("/track");
        Mockito.when(trackCommand.supports(update)).thenReturn(true);

        Command command = userMessageProcessor.process(update);
        assertTrue(command instanceof TrackCommand);
    }

    @Test
    public void unTrackCommandTest() {
        Update update = Mockito.mock(Update.class);
        Message mockMessage = Mockito.mock(Message.class);
        Mockito.when(update.message()).thenReturn(mockMessage);
        Mockito.when(mockMessage.text()).thenReturn("/untrack");
        Mockito.when(untrackCommand.supports(update)).thenReturn(true);

        Command command = userMessageProcessor.process(update);
        assertTrue(command instanceof UntrackCommand);
    }
}
