package edu.java.domain.jdbc;

import edu.java.domain.dao.LinkDao;
import edu.java.domain.dto.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@SuppressWarnings("MultipleStringLiterals")
public class LinkDaoJdbc implements LinkDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String ADD_SQL = "INSERT INTO LINK_TABLE (url, update_time) "
        + "VALUES (?, ?)";
    private static final String ADD_LINK_SQL = "INSERT INTO LINK_TABLE (id, url, update_time) "
        + "VALUES (?, ?, ?)";
    private static final String REMOVE_SQL = "DELETE FROM LINK_TABLE "
        + "WHERE url = ?";
    private static final String REMOVE_FROM_CHAT_LINK_TABLE_SQL = "DELETE FROM CHAT_LINK "
        + "WHERE link_id IN (SELECT id FROM LINK_TABLE WHERE url = ?)";
    private static final String FIND_BY_URI_SQL = "SELECT * FROM LINK_TABLE "
        + "WHERE url = ?";
    private static final String FIND_BY_CHECK_TIME_SQL = "SELECT * FROM LINK_TABLE WHERE update_time <= ?";


    public void add(URI url, OffsetDateTime updateTime) {
        jdbcTemplate.update(ADD_SQL, url.toString(), updateTime);
    }

    public void add(URI url) {
        add(url, OffsetDateTime.now());
    }

    public void add(Link link) {
        jdbcTemplate.update(ADD_LINK_SQL, link.getId(), link.getUrl().toString(), link.getUpdate_time());
    }


    @Transactional
    public void remove(URI url) {
        jdbcTemplate.update(REMOVE_SQL, url.toString());
        jdbcTemplate.update(REMOVE_FROM_CHAT_LINK_TABLE_SQL, url.toString());
    }


    @Transactional
    public List<Link> findByCheckTime(OffsetDateTime time) {
        return jdbcTemplate.query(FIND_BY_CHECK_TIME_SQL, (resultSet, rowNum) -> {
            int linkId = resultSet.getInt("id");
            String url = resultSet.getString("url");
            OffsetDateTime updateTime = resultSet.getObject("update_time", OffsetDateTime.class);
            return new Link(linkId, URI.create(url), updateTime);
        }, time);
    }


    @Transactional
    public Link findByUri(URI uri) {
        List<Link> result =
            jdbcTemplate.query(FIND_BY_URI_SQL, new Object[]{uri.toString()}, new BeanPropertyRowMapper<>(Link.class));
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

}

