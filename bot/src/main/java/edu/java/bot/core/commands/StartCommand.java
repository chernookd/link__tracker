package edu.java.bot.core.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.core.MySendMessage;
import edu.java.bot.core.TrackList;
import edu.java.bot.utils.MessageUtils;
import java.util.HashSet;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {

    private final TrackList list;
    private static final String DESCRIPTION = "Регестрация пользователя\n";
    private static final String COMMAND = "/start";

    public StartCommand(TrackList list) {
        this.list = list;
    }

    public static String getCommandText() {
        return COMMAND;
    }

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public MySendMessage handle(@NotNull Update update) {
        StringBuilder listCommandMessage = new StringBuilder();
        Long userID = MessageUtils.getUserId(update.message());

        if (list.getUsersWithLinks() == null || list.getUsersWithLinks().isEmpty()
            || !list.getUsersWithLinks().containsKey(userID)) {

            list.getUsersWithLinks().put(userID, new HashSet<>());
            listCommandMessage = new StringBuilder("Отслеживание началось");
            return new MySendMessage(update.message().chat().id(), listCommandMessage.toString());
        }

        if (list.getUsersWithLinks().containsKey(userID)) {
            listCommandMessage = new StringBuilder("Аккаунт найден\n");
            listCommandMessage.append("Прошлые ссылки : \n");

            for (String link : list.getUsersWithLinks().get(userID)) {
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
