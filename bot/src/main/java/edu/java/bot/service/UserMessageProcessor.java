package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.command.Command;

public interface UserMessageProcessor {
    Command process(Update update);
}
