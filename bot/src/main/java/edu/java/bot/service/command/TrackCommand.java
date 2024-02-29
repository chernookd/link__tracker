package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.InMemoryTrackRepository;
import edu.java.bot.utils.LinkValidator;
import edu.java.bot.utils.MessageUtils;
import edu.java.bot.utils.UpdateUtils;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    private final InMemoryTrackRepository list;
    private static final String DESCRIPTION =  " 'ссылка' - отслеживает заданную ссылку\n";
    private static final String COMMAND = "/track";

    public TrackCommand(InMemoryTrackRepository list) {
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

    @Override
    public SendMessage handle(Update update) {
        String link = MessageUtils.getLink(update.message(), COMMAND);
        Long userID = MessageUtils.getUserId(update.message());

        if (list.getUsersWithLinks() == null || list.getUsersWithLinks().isEmpty()
            || !list.getUsersWithLinks().containsKey(userID)) {
            return new SendMessage(UpdateUtils.getChatId(update), "Зарегестрируйтесь /start");
        } else if (LinkValidator.isValidLink(link)) {
            list.getUsersWithLinks().get(userID).add(link);
            return new SendMessage(UpdateUtils.getChatId(update), "Начал отслеживание ссылки: " + link);
        }
        return new SendMessage(UpdateUtils.getChatId(update), "Некорректная ссылка " + link);
    }

    public boolean supports(Update update) {
        return MessageUtils.getCommand(update.message()).equalsIgnoreCase(COMMAND);
    }
}
