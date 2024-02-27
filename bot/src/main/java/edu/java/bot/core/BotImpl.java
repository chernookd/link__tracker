package edu.java.bot.core;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.core.commands.Command;
import edu.java.bot.core.commands.CommandBuilder;
import edu.java.bot.core.commands.HelpCommand;
import edu.java.bot.core.commands.ListCommand;
import edu.java.bot.core.commands.StartCommand;
import edu.java.bot.core.commands.TrackCommand;
import edu.java.bot.core.commands.UntrackCommand;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BotImpl implements Bot {

    private static final int INDEX_OF_START_COMMAND = 0;
    private static final int INDEX_OF_HELP_COMMAND = 1;
    private static final int INDEX_OF_LIST_COMMAND = 2;
    private static final int INDEX_OF_TRACK_COMMAND = 3;
    private static final int INDEX_OF_UNTRACK_COMMAND = 4;


    private final ApplicationConfig applicationConfig;
    private static TelegramBot bot;
    private static final int NUM_OF_COMMANDS = 5;
    Command[] commands = new Command[NUM_OF_COMMANDS];
    private final TrackList trackList;

    @Autowired
    public BotImpl(TrackList trackList, ApplicationConfig applicationConfig, StartCommand startCommand,
        HelpCommand helpCommand, ListCommand listCommand, TrackCommand trackCommand, UntrackCommand untrackCommand) {
        this.applicationConfig = applicationConfig;
        this.trackList = trackList;

        commands[INDEX_OF_START_COMMAND] = startCommand;
        commands[INDEX_OF_HELP_COMMAND] = helpCommand;
        commands[INDEX_OF_LIST_COMMAND] = listCommand;
        commands[INDEX_OF_TRACK_COMMAND] = trackCommand;
        commands[INDEX_OF_UNTRACK_COMMAND] = untrackCommand;
    }

    @PostConstruct
    @Override
    public void start() {
        bot = new TelegramBot(applicationConfig.getTelegramProperties().getTelegramToken());
        BotCommand[] botCommands = createMyCommands();
        bot.execute(new SetMyCommands(botCommands));


        bot.setUpdatesListener(updates -> {
            process(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (e.response() != null) {
                e.response().errorCode();
                e.response().description();
            }
        });
    }

    private BotCommand[] createMyCommands() {
        BotCommand[] botCommands = new BotCommand[NUM_OF_COMMANDS];
        for (int i = 0; i < NUM_OF_COMMANDS; i++) {
            botCommands[i] = (new BotCommand(commands[i].command(), commands[i].description()));
        }
        return botCommands;
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        UserMessageProcessor userMessageProcessor = new UserMessageProcessorImpl(new CommandBuilder(trackList));

        for (Update update : updates) {
            if (update.message() != null && update.message().text() != null && !update.message().text().isEmpty()) {
                String messageText = update.message().text();
                Command command = userMessageProcessor.process(messageText);
                MySendMessage mySendMessage = command.handle(update);
                SendMessage sendMessage = new SendMessage(mySendMessage.chatId(), mySendMessage.message());
                bot.execute(sendMessage);
            }
        }
        return 0;
    }

    @Override
    public void close() {
        bot.shutdown();
    }


}
