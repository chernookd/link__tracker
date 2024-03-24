package edu.java.domain.dao;

import edu.java.domain.dto.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkDao {
    Link findByUri(URI uri);

    List<Link> findByCheckTime(OffsetDateTime time);

    void remove(URI url);

    void add(Link link);

    void add(URI url);

    void add(URI url, OffsetDateTime updateTime);
}
