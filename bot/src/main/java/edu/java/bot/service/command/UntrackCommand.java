package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.scrapperClientService.ScrapperClientService;
import edu.java.bot.utils.LinkValidator;
import edu.java.bot.utils.MessageUtils;
import java.net.URI;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {
    private static final String DESCRIPTION =  " 'ссылка' - удаляет заданную ссылку из списка отслеживаемых\n";
    private static final String COMMAND = "/untrack";
    private final ScrapperClientService scrapperClientService;


    public UntrackCommand(ScrapperClientService scrapperClientService) {
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
    public SendMessage handle(Update update) {
        String link = MessageUtils.getLink(update.message(), COMMAND);
        Long chatId = MessageUtils.getChatId(update.message());

        URI uri = LinkValidator.isValidLink(link);

        if (uri != null) {
            scrapperClientService.removeLink(chatId, uri);
            return new SendMessage(chatId, "Прекратил отслеживание ссылки: " + link);
        }
        return new SendMessage(chatId, "Ссылка неправильная");
    }
}
