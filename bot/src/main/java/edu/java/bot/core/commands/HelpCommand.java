package edu.java.bot.core.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.core.MySendMessage;
import edu.java.bot.utils.MessageUtils;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private static final String COMMAND = "/help";
    private static final String DESCRIPTION = "Выводит список доступных команд";
    private final CommandBuilder builder;

    public HelpCommand(CommandBuilder commandBuilder) {
        this.builder = commandBuilder;
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
        Long chatId = MessageUtils.getChatId(update.message());
        StringBuilder helpCommandMessage = new StringBuilder();

        List<Command> commands = builder.buldCommandsList();
        for (Command command : commands) {
            helpCommandMessage.append(command.command()).append(" - ").append(command.description()).append("\n");
        }

        return new MySendMessage(chatId, helpCommandMessage.toString());
    }

    @Override
    public boolean supports(Update update) {
        return update.message().text().equals(COMMAND);
    }

    @Override
    public BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
