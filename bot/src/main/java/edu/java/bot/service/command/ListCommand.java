package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.InMemoryTrackRepository;
import edu.java.bot.utils.MessageUtils;
import edu.java.bot.utils.UpdateUtils;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {

    private final InMemoryTrackRepository list;
    private static final String DESCRIPTION = "Выводит список отслеживаемых ссылок\n";
    private static final String COMMAND = "/list";
    private static final String EMPTY_SET_MESSAGE = "Вы не остлеживаете ни одной ссылки";



    public ListCommand(InMemoryTrackRepository list) {
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
        Long userID = MessageUtils.getUserId(update.message());
        StringBuilder listCommandMessage = new StringBuilder("Ccылки :\n");
        int linksNum = 0;

        if (list.getUsersWithLinks() == null || list.getUsersWithLinks().isEmpty()
            || !list.getUsersWithLinks().containsKey(userID)) {
            return new SendMessage(UpdateUtils.getChatId(update), "Зарегестрируйтесь /start");
        }

        for (String link : list.getUsersWithLinks().get(userID)) {
            listCommandMessage.append(link).append("\n");
            linksNum += 1;
        }
        if (linksNum == 0) {
            listCommandMessage = new StringBuilder(EMPTY_SET_MESSAGE);
        }

        return new SendMessage(UpdateUtils.getChatId(update), listCommandMessage.toString());
    }

    public boolean supports(Update update) {
        return MessageUtils.getCommand(update.message()).equalsIgnoreCase(COMMAND);
    }

}
