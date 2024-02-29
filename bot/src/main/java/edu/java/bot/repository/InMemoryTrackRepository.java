package edu.java.bot.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import org.springframework.stereotype.Repository;

@Getter @Repository
public class InMemoryTrackRepository {

    private Map<Long, Set<String>> usersWithLinks;

    public InMemoryTrackRepository() {
        this.usersWithLinks = new HashMap<>();
    }

}
