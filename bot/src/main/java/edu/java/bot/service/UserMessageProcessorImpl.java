package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.command.Command;
import edu.java.bot.utils.UpdateUtils;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private static final String ERROR_TEXT_MESSAGE = "ERROR";
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

    public SendMessage processUpdate(Update update) {
        if (UpdateUtils.isValidUpdate(update)) {
            Command command = process(update);
            SendMessage sendMessage;

            if (command != null) {
                sendMessage = command.handle(update);
            } else {
                sendMessage = new SendMessage(UpdateUtils.getChatId(update), ERROR_TEXT_MESSAGE);
            }
            return sendMessage;
        }
        return new SendMessage(UpdateUtils.getChatId(update), ERROR_TEXT_MESSAGE);
    }
}
