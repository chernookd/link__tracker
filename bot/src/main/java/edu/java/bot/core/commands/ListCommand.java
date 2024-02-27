package edu.java.bot.core.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.core.MySendMessage;
import edu.java.bot.core.TrackList;
import edu.java.bot.utils.MessageUtils;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {

    private final TrackList list;
    private static final String DESCRIPTION = "Выводит список отслеживаемых ссылок\n";
    private static final String COMMAND = "/list";
    private static final String EMPTY_SET_MESSAGE = "Вы не остлеживаете ни одной ссылки";



    public ListCommand(TrackList list) {
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
    public MySendMessage handle(Update update) {
        Long userID = MessageUtils.getUserId(update.message());
        StringBuilder listCommandMessage = new StringBuilder("Ccылки :\n");
        int linksNum = 0;

        if (list.getUsersWithLinks() == null || list.getUsersWithLinks().isEmpty()
            || !list.getUsersWithLinks().containsKey(userID)) {
            return new MySendMessage(update.message().chat().id(), "Зарегестрируйтесь /start");
        }

        for (String link : list.getUsersWithLinks().get(userID)) {
            listCommandMessage.append(link).append("\n");
            linksNum += 1;
        }
        if (linksNum == 0) {
            listCommandMessage = new StringBuilder(EMPTY_SET_MESSAGE);
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
