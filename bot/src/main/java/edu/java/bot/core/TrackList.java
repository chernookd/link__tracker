package edu.java.bot.core;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TrackList {

    private Set<String> trackList = new HashSet<>();

    public TrackList(Set<String> trackList) {
        this.trackList = trackList;
    }

    public TrackList() {}

    public void track(String link) {
        trackList.add(link);
    }

    public void untrack(String link) {
        trackList.remove(link);
    }
}
