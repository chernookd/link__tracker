package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.utils.MessageUtils;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private static final String COMMAND = "/help";
    private static final String DESCRIPTION = "Выводит список доступных команд";

    private final Command[] commandsArr;

    public HelpCommand(Command[] commands) {
        this.commandsArr = commands;
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
        Long chatId = MessageUtils.getChatId(update.message());
        StringBuilder helpCommandMessage = new StringBuilder();

        List<Command> commands = Arrays.stream(commandsArr).toList();

        for (Command command : commands) {
            helpCommandMessage.append(command.command()).append(" - ").append(command.description()).append("\n");
        }

        return new SendMessage(chatId, helpCommandMessage.toString());
    }
}
