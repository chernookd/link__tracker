package edu.java.bot.service.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.scrapperClient.dto.LinkResponse;
import edu.java.bot.scrapperClient.dto.ListLinksResponse;
import edu.java.bot.service.scrapperClientService.ScrapperClientService;
import edu.java.bot.utils.UpdateUtils;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {

    private static final String DESCRIPTION = "Выводит список отслеживаемых ссылок\n";
    private static final String COMMAND = "/list";
    private static final String EMPTY_SET_MESSAGE = "Вы не остлеживаете ни одной ссылки";
    private final ScrapperClientService scrapperClientService;


    public ListCommand(ScrapperClientService scrapperClientService) {
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
        Long chatID = UpdateUtils.getChatId(update);
        int linksNum = 0;
        StringBuilder listCommandMessage = new StringBuilder("Ccылки :\n");

        ListLinksResponse listLinksResponse = scrapperClientService.getLinks(chatID);



        if (listLinksResponse == null || listLinksResponse.getLinks().isEmpty()) {
            return new SendMessage(chatID, listCommandMessage.toString());
        }
        for (LinkResponse link : listLinksResponse.getLinks()) {
            listCommandMessage.append(link.getUrl().toString()).append("\n");
            linksNum += 1;
        }
        if (linksNum == 0) {
            listCommandMessage = new StringBuilder(EMPTY_SET_MESSAGE);
        }

        return new SendMessage(chatID, listCommandMessage.toString());
    }
}
