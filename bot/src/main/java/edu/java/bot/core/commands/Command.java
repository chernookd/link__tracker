package edu.java.bot.core.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.core.MySendMessage;

public interface Command {
    String command();

    String description();

    MySendMessage handle(Update update);

    default boolean supports(Update update) {
        return true;
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
