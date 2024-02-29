package edu.java.bot.utils;

import com.pengrad.telegrambot.model.Message;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageUtils {

    public String getLink(Message message, String command) {
        return message.text().substring(command.length()).trim();
    }

    public Long getUserId(Message message) {
        return message.from().id();
    }

    public Long getChatId(Message message) {
        return message.chat().id();
    }

    public String getCommand(Message message) {
        String textMessage = message.text();
        int commandEndIndex = textMessage.indexOf(' ');
        if (commandEndIndex == -1) {
            return textMessage;
        } else {
            return textMessage.substring(0, commandEndIndex);
        }
    }

}
