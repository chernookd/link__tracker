package edu.java.bot.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class TrackList {

    private Map<Long, Set<String>> usersWithLinks;

    public TrackList() {
        this.usersWithLinks = new HashMap<>();
    }
}
