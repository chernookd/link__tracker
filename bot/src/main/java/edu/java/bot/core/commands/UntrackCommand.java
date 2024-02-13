package edu.java.bot.core.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.core.MySendMessage;
import edu.java.bot.utils.Utils;

public class UntrackCommand implements Command {

    private final ApplicationConfig applicationConfig;

    public UntrackCommand(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return " 'ссылка' - удаляет заданную ссылку из списка отслеживаемых\n";
    }

    @Override
    public MySendMessage handle(Update update) {
        String link = update.message().text().substring(command().length()).trim();
        Long userID = update.message().from().id();
        Utils utils = new Utils();

        if (applicationConfig.getUsersWithTrackList() == null || applicationConfig.getUsersWithTrackList().isEmpty()
            || !applicationConfig.getUsersWithTrackList().containsKey(userID)) {
            return new MySendMessage(update.message().chat().id(), "Зарегестрируйтесь /start");
        }

        if (utils.isValidLink(link)) {
            applicationConfig.getUsersWithTrackList().get(userID).untrack(link);
            return new MySendMessage(update.message().chat().id(), "Удалил ссылку " + link);
        }
        return new MySendMessage(update.message().chat().id(), "Некорректная ссылка" + " link");
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
