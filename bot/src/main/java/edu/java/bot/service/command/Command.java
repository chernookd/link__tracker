package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.utils.MessageUtils;

public interface Command {
    String command();

    String description();

    SendMessage handle(Update update);

    default boolean supports(Update update) {
        return MessageUtils.getCommand(update.message()).equalsIgnoreCase(this.command());
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
