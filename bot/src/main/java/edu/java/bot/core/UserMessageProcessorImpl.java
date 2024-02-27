package edu.java.bot.core;

import edu.java.bot.core.commands.Command;
import edu.java.bot.core.commands.CommandBuilder;
import edu.java.bot.core.commands.HelpCommand;
import edu.java.bot.core.commands.ListCommand;
import edu.java.bot.core.commands.StartCommand;
import edu.java.bot.core.commands.TrackCommand;
import edu.java.bot.core.commands.UntrackCommand;
import edu.java.bot.utils.MessageUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class UserMessageProcessorImpl implements UserMessageProcessor {
    CommandBuilder commandBuilder;

    public UserMessageProcessorImpl(CommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    @Override
    public List<String> commands() {
        List<String> commands = new ArrayList<>();

        commands.add(HelpCommand.getCommandText());
        commands.add(StartCommand.getCommandText());
        commands.add(ListCommand.getCommandText());
        commands.add(TrackCommand.getCommandText());
        commands.add(UntrackCommand.getCommandText());

        return commands;
    }

    @Override
    public Command process(String message) {
        String commandInMessage = MessageUtils.getCommand(message);
        List<String> commands = commands();

        for (String command : commands) {
            if (command.equals(commandInMessage)) {
                Command resultCommand = commandBuilder.buildCommand(commandInMessage);
                return resultCommand;
            }
        }

        return new HelpCommand(commandBuilder);
    }
}
