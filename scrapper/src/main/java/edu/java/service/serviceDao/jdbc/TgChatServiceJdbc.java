package edu.java.service.serviceDao.jdbc;

import edu.java.domain.jdbc.ChatDaoJdbc;
import edu.java.service.serviceDao.TgChatService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TgChatServiceJdbc implements TgChatService {

    private final ChatDaoJdbc chatDaoJdbc;

    @Override
    public void register(long tgChatId) {
        chatDaoJdbc.add(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        chatDaoJdbc.remove(tgChatId);
    }
}
