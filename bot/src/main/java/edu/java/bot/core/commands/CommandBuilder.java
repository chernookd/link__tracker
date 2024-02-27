package edu.java.bot.core.commands;

import edu.java.bot.core.TrackList;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandBuilder {

    TrackList trackList;

    @Autowired
    public CommandBuilder(TrackList trackList) {
        this.trackList = trackList;
    }

    public Command buildCommand(String message) {
        return switch (message) {
            case "/start" -> new StartCommand(trackList);
            case "/list" -> new ListCommand(trackList);
            case "/track" -> new TrackCommand(trackList);
            case "/untrack" -> new UntrackCommand(trackList);
            case "/help" -> new HelpCommand(this);
            default -> throw new IllegalArgumentException("Unknown command: " + message);
        };
    }

    public List<Command> buldCommandsList() {
        List<Command> commands = new ArrayList<>();

        commands.add(buildCommand(HelpCommand.getCommandText()));
        commands.add(buildCommand(StartCommand.getCommandText()));
        commands.add(buildCommand(ListCommand.getCommandText()));
        commands.add(buildCommand(TrackCommand.getCommandText()));
        commands.add(buildCommand(UntrackCommand.getCommandText()));

        return commands;
        }

}
