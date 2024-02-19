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
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@SuppressWarnings({"RegexpSinglelineJava", "MagicNumber"})
public class BotImpl implements Bot {

    @NonNull
    private final ApplicationConfig applicationConfig;
    private static TelegramBot bot;
    private static final int NUM_OF_COMMANDS = 5;

    @PostConstruct
    @Override
    public void start() {
        bot = new TelegramBot(applicationConfig.telegramToken);
        BotCommand[] botCommands = createMyCommands();

        bot.execute(new SetMyCommands(botCommands));


        bot.setUpdatesListener(updates -> {
            process(updates);

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (e.response() != null) {
                e.response().errorCode();
                e.response().description();
            } else {
                e.printStackTrace();
            }
        });
    }

    private static BotCommand[] createMyCommands() {
        BotCommand[] botCommands = new BotCommand[NUM_OF_COMMANDS];

        botCommands[0] = (new BotCommand("/start", "start"));
        botCommands[1] = (new BotCommand("/help", "help"));
        botCommands[2] = (new BotCommand("/list", "list"));
        botCommands[3] = (new BotCommand("/track", "track"));
        botCommands[4] = (new BotCommand("/untrack", "untrack"));

        return botCommands;
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        UserMessageProcessor userMessageProcessor = new UserMessageProcessorImpl(applicationConfig);

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

    }


}
