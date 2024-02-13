package edu.java.bot.core.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.core.MySendMessage;

public class UnknowCommand implements Command {

    private static final String HELP_COMMAND = "/help";

    String message;

    public UnknowCommand(String message) {
        this.message = message;
    }

    @Override
    public String command() {
        return "/unknown";
    }

    @Override
    public String description() {
        return "Неизвестная команда " + HELP_COMMAND;
    }

    @Override
    public MySendMessage handle(Update update) {
        return new MySendMessage(update.message().chat().id(), "Неизвестная команда /help");
    }

    @Override
    public boolean supports(Update update) {
        return Command.super.supports(update);
    }

    @Override
    public BotCommand toApiCommand() {
        return Command.super.toApiCommand();
    }
}
