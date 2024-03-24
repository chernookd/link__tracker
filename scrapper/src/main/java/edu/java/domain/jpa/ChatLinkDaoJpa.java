package edu.java.domain.jpa;

import edu.java.domain.jpa.entity.ChatEntity;
import edu.java.domain.jpa.entity.ChatLinkEntity;
import edu.java.domain.jpa.entity.LinkEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatLinkDaoJpa extends JpaRepository<ChatLinkEntity, Long> {

    List<ChatLinkEntity> findAllByChat(ChatEntity chat);

    List<ChatLinkEntity> findAllByLink(LinkEntity link);

    void deleteAllByChatId(Long chatId);

    boolean existsByChatAndLink(ChatEntity chat, LinkEntity link);

    Long deleteByChatAndLink(ChatEntity chat, LinkEntity link);
}
