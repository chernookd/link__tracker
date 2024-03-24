package edu.java.domain.dao;

import edu.java.domain.dto.Chat;
import java.util.List;

public interface ChatDao {
    boolean add(Long id);

    void remove(Long id);

    List<Chat> findAll();

    List<Chat> findById(Long id);
}
