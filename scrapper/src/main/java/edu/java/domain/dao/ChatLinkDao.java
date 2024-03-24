package edu.java.domain.dao;

import edu.java.domain.dto.ChatLink;
import edu.java.domain.dto.Link;
import java.util.List;

@SuppressWarnings("ParameterName")
public interface ChatLinkDao {
    boolean add(Long chat_id, Long link_id);

    void remove(Long chat_id, Long link_id);

    List<ChatLink> findAllChatByLink(Long link_id);

    List<Link> findAllLinks(Long chatId);
}
