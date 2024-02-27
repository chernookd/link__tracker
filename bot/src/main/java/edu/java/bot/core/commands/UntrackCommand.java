package edu.java.bot.core.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.core.MySendMessage;
import edu.java.bot.core.TrackList;
import edu.java.bot.utils.LinkValidator;
import edu.java.bot.utils.MessageUtils;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {

    private final TrackList list;
    private static final String DESCRIPTION =  " 'ссылка' - удаляет заданную ссылку из списка отслеживаемых\n";
    private static final String COMMAND = "/untrack";

    public UntrackCommand(TrackList list) {
        this.list = list;
    }

    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    public static String getCommandText() {
        return COMMAND;
    }

    @Override
    public MySendMessage handle(Update update) {
        String link = MessageUtils.getLink(update.message(), COMMAND);
        Long userID = MessageUtils.getUserId(update.message());

        if (list.getUsersWithLinks() == null || list.getUsersWithLinks().isEmpty()
            || !list.getUsersWithLinks().containsKey(userID)) {
            return new MySendMessage(update.message().chat().id(), "Зарегестрируйтесь /start");
        }

        if (LinkValidator.isValidLink(link)) {
            list.getUsersWithLinks().get(userID).remove(link);
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
