package edu.java.bot.core.test;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.core.UserMessageProcessorImpl;
import edu.java.bot.core.commands.Command;
import edu.java.bot.core.commands.HelpCommand;
import edu.java.bot.core.commands.ListCommand;
import edu.java.bot.core.commands.StartCommand;
import edu.java.bot.core.commands.TrackCommand;
import edu.java.bot.core.commands.UnknowCommand;
import edu.java.bot.core.commands.UntrackCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserMessageProcessorImplTest {

    private ApplicationConfig applicationConfig;
    private UserMessageProcessorImpl userMessageProcessor;

    @BeforeEach
    public void setUp() {
        applicationConfig = Mockito.mock(ApplicationConfig.class);
        userMessageProcessor = new UserMessageProcessorImpl(applicationConfig);
    }

    @Test
    public void testProcess() {
        Command command = userMessageProcessor.process("/start");
        assertTrue(command instanceof StartCommand);

        command = userMessageProcessor.process("aaa");
        assertTrue(command instanceof UnknowCommand);

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
