package edu.java.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.UserMessageProcessorImpl;
import edu.java.bot.service.command.Command;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class BotImpl implements Bot {
    private final TelegramBot bot;
    private final Command[] commands;
    private final UserMessageProcessorImpl userMessageProcessor;
    private final static String ERROR_TEXT_MESSAGE = "ERROR";

    public BotImpl(ApplicationConfig applicationConfig, Command[] commands,
        UserMessageProcessorImpl userMessageProcessor) {
        this.commands = commands;
        this.userMessageProcessor = userMessageProcessor;

        bot = new TelegramBot(applicationConfig.telegramToken());
    }

    @PostConstruct
    @Override
    public void start() {
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
        return Arrays.stream(commands)
            .map(Command::toApiCommand)
            .toArray(BotCommand[]::new);
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            SendMessage sendMessage = userMessageProcessor.processUpdate(update);
            bot.execute(sendMessage);
        }
        return 0;
    }

    @Override
    public void close() {
        bot.shutdown();
    }
}
