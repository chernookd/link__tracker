package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.command.Command;
import java.util.Arrays;
import org.springframework.stereotype.Component;


@Component
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private final Command[] commands;

    public UserMessageProcessorImpl(Command[] commands) {
        this.commands = commands;
    }

    @Override
    public Command process(Update update) {

        for (Command command : commands) {
            if (command.supports(update)) {
                return command;
            }
        }

        return Arrays.stream(commands)
            .filter(command -> command.command().equalsIgnoreCase("/help"))
            .findFirst()
            .orElse(null);
    }
}
