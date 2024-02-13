package edu.java.bot.core.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.core.MySendMessage;
import java.util.ArrayList;
import java.util.List;

public class HelpCommand implements Command {

    private static final String HELP_COMMAND = "/help";

    private final ApplicationConfig applicationConfig;

    public HelpCommand(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public List<Command> buildCommands() {
        List<Command> commands = new ArrayList<>();
        commands.add(new HelpCommand(applicationConfig));
        commands.add(new ListCommand(applicationConfig));
        commands.add(new StartCommand(applicationConfig));
        commands.add(new UntrackCommand(applicationConfig));
        commands.add(new TrackCommand(applicationConfig));

        return commands;
    }

    @Override
    public String command() {
        return HELP_COMMAND;
    }

    @Override
    public String description() {
        return "Выводит список доступных команд";
    }

    @Override
    public MySendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        StringBuilder helpCommandMessage = new StringBuilder();
        List<Command> commands = buildCommands();
        for (Command command : commands) {
            helpCommandMessage.append(command.command()).append(" - ").append(command.description()).append("\n");
        }

        return new MySendMessage(chatId, helpCommandMessage.toString());
    }

    @Override
    public boolean supports(Update update) {
        return update.message().text().equals(HELP_COMMAND);
    }

    @Override
    public BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
