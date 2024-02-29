package edu.java.bot.utils;

import com.pengrad.telegrambot.model.Update;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UpdateUtils {

    public Long getChatId(Update update) {
        if (update.message() != null && update.message().chat() != null) {
            return update.message().chat().id();
        }
        return null;
    }

    public boolean isValidUpdate(Update update) {
        return update != null && update.message() != null && update.message().text() != null;
    }
}
