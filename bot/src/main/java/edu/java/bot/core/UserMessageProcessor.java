package edu.java.bot.core;

import edu.java.bot.core.commands.Command;
import java.util.List;

public interface UserMessageProcessor {
    List<Command> commands();

    Command process(String message);
}
