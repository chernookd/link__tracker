package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.InMemoryTrackRepository;
import edu.java.bot.utils.MessageUtils;
import java.util.HashSet;
import edu.java.bot.utils.UpdateUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {

    private final InMemoryTrackRepository list;
    private static final String DESCRIPTION = "Регестрация пользователя\n";
    private static final String COMMAND = "/start";

    public StartCommand(InMemoryTrackRepository list) {
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
    public SendMessage handle(@NotNull Update update) {
        StringBuilder listCommandMessage = new StringBuilder();
        Long userID = MessageUtils.getUserId(update.message());

        if (list.getUsersWithLinks() == null || list.getUsersWithLinks().isEmpty()
            || !list.getUsersWithLinks().containsKey(userID)) {

            list.getUsersWithLinks().put(userID, new HashSet<>());
            listCommandMessage = new StringBuilder("Отслеживание началось");
            return new SendMessage(UpdateUtils.getChatId(update), listCommandMessage.toString());
        }

        if (list.getUsersWithLinks().containsKey(userID)) {
            listCommandMessage = new StringBuilder("Аккаунт найден\n");
            listCommandMessage.append("Прошлые ссылки : \n");

            for (String link : list.getUsersWithLinks().get(userID)) {
                listCommandMessage.append(link).append("\n");
            }
        }

        return new SendMessage(UpdateUtils.getChatId(update), listCommandMessage.toString());
    }

    public boolean supports(Update update) {
        return MessageUtils.getCommand(update.message()).equalsIgnoreCase(COMMAND);
    }
}
