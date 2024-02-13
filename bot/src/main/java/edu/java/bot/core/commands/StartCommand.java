package edu.java.bot.core.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.core.MySendMessage;
import edu.java.bot.core.TrackList;
import org.jetbrains.annotations.NotNull;

public class StartCommand implements Command {

    private final ApplicationConfig applicationConfig;

    public StartCommand(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Регестрация пользователя\n";
    }

    @Override
    public MySendMessage handle(@NotNull Update update) {
        StringBuilder listCommandMessage = new StringBuilder();
        Long userID = update.message().from().id();
        if (applicationConfig.getUsersWithTrackList() == null || applicationConfig.getUsersWithTrackList().isEmpty()
            || !applicationConfig.getUsersWithTrackList().containsKey(userID)) {
            applicationConfig.getUsersWithTrackList().put(userID, new TrackList());
            listCommandMessage = new StringBuilder("Отслеживание началось");
            return new MySendMessage(update.message().chat().id(), listCommandMessage.toString());
        }

        if (applicationConfig.getUsersWithTrackList().containsKey(userID)) {
            //applicationConfig.usersWithTrackList.put(userID, new TrackList());
            listCommandMessage = new StringBuilder("Аккаунт найден\n");
            listCommandMessage.append("Прошлые ссылки : \n");

            for (String link : applicationConfig.getUsersWithTrackList().get(userID).getTrackList()) {
                listCommandMessage.append(link).append("\n");
            }
        }

        return new MySendMessage(update.message().chat().id(), listCommandMessage.toString());
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
