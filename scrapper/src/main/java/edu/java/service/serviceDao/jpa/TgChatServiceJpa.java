package edu.java.service.serviceDao.jpa;

import edu.java.domain.jpa.ChatDaoJpa;
import edu.java.domain.jpa.entity.ChatEntity;
import edu.java.service.serviceDao.TgChatService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TgChatServiceJpa implements TgChatService {

    private final ChatDaoJpa chatDaoJpa;

    @Override
    public void register(long tgChatId) {
        ChatEntity chat = new ChatEntity(tgChatId);
        chatDaoJpa.save(chat);
    }

    @Override
    public void unregister(long tgChatId) {
        if (chatDaoJpa.existsById(tgChatId)) {
            throw new RuntimeException();
        } else {
            ChatEntity chat = new ChatEntity(tgChatId);
            chatDaoJpa.save(chat);
        }
    }
}
