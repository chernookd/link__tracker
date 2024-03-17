package edu.java.domain.dao;

import edu.java.domain.dto.ChatLink;
import edu.java.domain.dto.Link;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@SuppressWarnings("ParameterName")
public class ChatLinkDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String ADD_SQL = "INSERT INTO CHAT_LINK (chat_id, link_id) VALUES (?, ?)";
    private static final String REMOVE_SQL = "DELETE FROM CHAT_LINK WHERE chat_id = ? AND link_id = ?";
    private static final String FIND_LIST_OF_LINK_SQL =
        "SELECT l.* FROM LINK_TABLE l "
            + "JOIN CHAT_LINK ON l.id = CHAT_LINK.link_id "
            + "WHERE chat_id = ?";
    private static final String FIND_ALL_CHAT_BY_LINK_SQL = "SELECT * FROM CHAT_LINK WHERE link_id = ?";

    @Transactional
    public boolean add(Long chat_id, Long link_id) {
        try {
            jdbcTemplate.update(ADD_SQL, chat_id, link_id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public void remove(Long chat_id, Long link_id) {
        jdbcTemplate.update(REMOVE_SQL, chat_id, link_id);
    }


    @Transactional
    public List<ChatLink> findAllChatByLink(Long link_id) {
        return jdbcTemplate.query(FIND_ALL_CHAT_BY_LINK_SQL, (rs, rowNum) -> {
            long chatId = rs.getLong("chat_id");
            long linkId = rs.getLong("link_id");
            long id = rs.getLong("id");
            return new ChatLink(id, chatId, linkId);
        }, link_id);
    }

    public List<Link> findAllLinks(Long chatId) {
        return jdbcTemplate.query(FIND_LIST_OF_LINK_SQL,
            new BeanPropertyRowMapper<>(Link.class),
            chatId);
    }

}



