package edu.java.bot.core.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.core.MySendMessage;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    private final ApplicationConfig applicationConfig;

    public ListCommand(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "Выводит список отслеживаемых ссылок\n";
    }

    @Override
    public MySendMessage handle(Update update) {
        Long userID = update.message().from().id();
        StringBuilder listCommandMessage = new StringBuilder("Ccылки :\n");
        int linksNum = 0;

        if (applicationConfig.getUsersWithTrackList() == null || applicationConfig.getUsersWithTrackList().isEmpty()
            || !applicationConfig.getUsersWithTrackList().containsKey(userID)) {
            return new MySendMessage(update.message().chat().id(), "Зарегестрируйтесь /start");
        }

        for (String link : applicationConfig.getUsersWithTrackList().get(userID).getTrackList()) {
            listCommandMessage.append(link).append("\n");
            linksNum += 1;
        }
        if (linksNum == 0) {
            listCommandMessage = new StringBuilder("Вы не остлеживаете ни одной ссылки");
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
