package edu.java.bot.core.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.core.MySendMessage;
import edu.java.bot.utils.Utils;

public class TrackCommand implements Command {
    private final ApplicationConfig applicationConfig;

    public TrackCommand(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return " 'ссылка' - отслеживает заданную ссылку\n";
    }

    @Override
    public MySendMessage handle(Update update) {
        String link = update.message().text().substring(command().length()).trim();
        Long userID = update.message().from().id();
        Utils utils = new Utils();

        if (applicationConfig.getUsersWithTrackList() == null || applicationConfig.getUsersWithTrackList().isEmpty()
            || !applicationConfig.getUsersWithTrackList().containsKey(userID)) {
            return new MySendMessage(update.message().chat().id(), "Зарегестрируйтесь /start");
        } else if (utils.isValidLink(link)) {
            applicationConfig.getUsersWithTrackList().get(userID).track(link);
            return new MySendMessage(update.message().chat().id(), "Начал отслеживание ссылки: " + link);
        }
        return new MySendMessage(update.message().chat().id(), "Некорректная ссылка " + link);
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
