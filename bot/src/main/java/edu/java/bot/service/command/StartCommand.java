package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.scrapperClientService.ScrapperClientService;
import edu.java.bot.utils.UpdateUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {

    private static final String DESCRIPTION = "Регестрация пользователя\n";
    private static final String COMMAND = "/start";
    private final ScrapperClientService scrapperClientService;


    public StartCommand(ScrapperClientService scrapperClientService) {
        this.scrapperClientService = scrapperClientService;
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
        Long chatID = UpdateUtils.getChatId(update);
        scrapperClientService.register(chatID);
        return new SendMessage(UpdateUtils.getChatId(update), "Пользователь зарегестрирован");
    }
}
