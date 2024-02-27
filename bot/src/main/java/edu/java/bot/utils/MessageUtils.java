package edu.java.bot.utils;

import com.pengrad.telegrambot.model.Message;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageUtils {

    public static String getLink(Message message, String command) {
        return message.text().substring(command.length()).trim();
    }

    public static Long getUserId(Message message) {
        return message.from().id();
    }

    public static Long getChatId(Message message) {
        return message.chat().id();
    }

    public static String getCommand(String message) {
        int commandEndIndex = message.indexOf(' ');
        if (commandEndIndex == -1) {
            return message;
        } else {
            return message.substring(0, commandEndIndex);
        }
    }

}
