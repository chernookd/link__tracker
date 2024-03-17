package edu.java.service.serviceDao.jdbc;

import edu.java.domain.dao.ChatDao;
import edu.java.service.serviceDao.TgChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TgChatServiceJdbc implements TgChatService {

    private final ChatDao chatDao;

    @Override
    public void register(long tgChatId) {
        chatDao.add(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        chatDao.remove(tgChatId);
    }
}
