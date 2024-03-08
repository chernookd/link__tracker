package edu.java.bot.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTrackRepository {

    private final Map<Long, Set<String>> usersWithLinks;

    public InMemoryTrackRepository() {
        this.usersWithLinks = new HashMap<>();
    }

    public boolean checkingForEmptiness() {
        return this.usersWithLinks.isEmpty();
    }

    public boolean containsKey(Long userId) {
        return this.usersWithLinks.containsKey(userId);
    }

    public Set<String> getByUserId(Long userID) {
        return this.usersWithLinks.get(userID);
    }

    public void putTrackSet(Long userID, Set<String> trackSet) {
        this.usersWithLinks.put(userID, trackSet);
    }

    public void put(Long userID, String link) {
        this.usersWithLinks.get(userID).add(link);
    }

    public void remove(Long userID, String link) {
        this.usersWithLinks.get(userID).remove(link);
    }


}
