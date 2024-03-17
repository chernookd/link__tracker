package edu.java.domain.dao;

import edu.java.domain.dto.Chat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@SuppressWarnings("MultipleStringLiterals")
public class ChatDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String ADD_SQL = "INSERT INTO CHAT_TABLE (id) "
        + "VALUES (?)";
    private static final String REMOVE_SQL = "DELETE FROM CHAT_TABLE "
        + "WHERE id = ?";
    private static final String REMOVE_FROM_CHAT_LINK_TABLE_SQL = "DELETE FROM CHAT_LINK "
        + "WHERE chat_id = ?";
    private static final String FIND_ALL_SQL = "SELECT * FROM CHAT_TABLE";

    private static final String FIND_BY_ID_SQL = "SELECT * FROM CHAT_TABLE "
        + "WHERE id = ?";


    @Transactional
    public boolean add(Long id) {
        if (findById(id).isEmpty()) {
            try {
                jdbcTemplate.update(ADD_SQL, id);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }


    @Transactional
    public void remove(Long id) {
        jdbcTemplate.update(REMOVE_SQL, id);
        jdbcTemplate.update(REMOVE_FROM_CHAT_LINK_TABLE_SQL, id);
    }


    @Transactional
    public List<Chat> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL,
            (resultSet, rowNum) -> {
                return new Chat(resultSet.getInt("id"));
            });
    }


    @Transactional
    public List<Chat> findById(Long id) {
        return jdbcTemplate.query(FIND_BY_ID_SQL, (resultSet, rowNum) -> {
            int chatId = resultSet.getInt("id");
            return new Chat(chatId);
        }, id);
    }
}

