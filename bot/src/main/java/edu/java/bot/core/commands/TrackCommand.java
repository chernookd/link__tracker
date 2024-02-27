package edu.java.bot.core.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.core.MySendMessage;
import edu.java.bot.core.TrackList;
import edu.java.bot.utils.LinkValidator;
import edu.java.bot.utils.MessageUtils;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    private final TrackList list;
    private static final String DESCRIPTION =  " 'ссылка' - отслеживает заданную ссылку\n";
    private static final String COMMAND = "/track";

    public TrackCommand(TrackList list) {
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
        String link = MessageUtils.getLink(update.message(), COMMAND);
        Long userID = MessageUtils.getUserId(update.message());

        if (list.getUsersWithLinks() == null || list.getUsersWithLinks().isEmpty()
            || !list.getUsersWithLinks().containsKey(userID)) {
            return new MySendMessage(update.message().chat().id(), "Зарегестрируйтесь /start");
        } else if (LinkValidator.isValidLink(link)) {
            list.getUsersWithLinks().get(userID).add(link);
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
