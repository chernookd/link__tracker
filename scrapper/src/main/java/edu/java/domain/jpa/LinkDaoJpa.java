package edu.java.domain.jpa;

import edu.java.domain.jpa.entity.LinkEntity;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkDaoJpa extends JpaRepository<LinkEntity, Long> {

    Optional<LinkEntity> findByUrl(String url);

    void deleteByUrl(String url);

    List<LinkEntity> findLinkEntitiesByUpdateTimeLessThanEqual(OffsetDateTime updateTime);
}
