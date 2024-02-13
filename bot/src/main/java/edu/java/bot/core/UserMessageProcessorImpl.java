package edu.java.bot.core;


import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.core.commands.Command;
import edu.java.bot.core.commands.HelpCommand;
import edu.java.bot.core.commands.ListCommand;
import edu.java.bot.core.commands.StartCommand;
import edu.java.bot.core.commands.TrackCommand;
import edu.java.bot.core.commands.UnknowCommand;
import edu.java.bot.core.commands.UntrackCommand;
import java.util.ArrayList;
import java.util.List;

public class UserMessageProcessorImpl implements UserMessageProcessor {

    ApplicationConfig applicationConfig;

    public UserMessageProcessorImpl(ApplicationConfig config) {
        this.applicationConfig = config;
    }

    @Override
    public List<Command> commands() {

        List<Command> commands = new ArrayList<>();
        commands.add(new HelpCommand(applicationConfig));
        commands.add(new ListCommand(applicationConfig));
        commands.add(new StartCommand(applicationConfig));
        commands.add(new UntrackCommand(applicationConfig));
        commands.add(new TrackCommand(applicationConfig));

        return commands;
    }

    @Override
    public Command process(String message) {
        List<Command> commands = commands();

        for (Command command: commands) {
            if (message.startsWith(command.command())) {
                return command;
            }
        }

        return new UnknowCommand(message);
    }
}
