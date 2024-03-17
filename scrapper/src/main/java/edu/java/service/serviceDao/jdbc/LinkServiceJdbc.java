package edu.java.service.serviceDao.jdbc;

import edu.java.controller.exception.DeleteLinkException;
import edu.java.controller.exception.FindLinkException;
import edu.java.domain.dao.ChatDao;
import edu.java.domain.dao.ChatLinkDao;
import edu.java.domain.dao.LinkDao;
import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Link;
import edu.java.service.serviceDao.LinkService;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkServiceJdbc implements LinkService {

    private final LinkDao linkDao;
    private final ChatDao chatDao;
    private final ChatLinkDao chatLinkDao;

    @Override
    public Link add(long tgChatId, URI url) {
        Chat chat = chatDao.findById(tgChatId).getFirst();
        if (chat == null) {
            chatDao.add(tgChatId);
        }

        linkDao.add(url);
        Link link = linkDao.findByUri(url);
        long linkId = link.getId();
        chatLinkDao.add(tgChatId, linkId);

        return link;
    }

    @Override
    public Link remove(long tgChatId, URI url) throws DeleteLinkException {
        Chat chat = chatDao.findById(tgChatId).getFirst();
        Link link = linkDao.findByUri(url);
        if (chat == null || link == null) {
            throw new DeleteLinkException();
        }

        linkDao.remove(url);
        chatLinkDao.remove(chat.getId(), link.getId());

        return link;
    }

    @Override
    public Collection<Link> listAll(long tgChatId) throws FindLinkException {
        List<Chat> chatList = chatDao.findById(tgChatId);
        if (chatList == null || chatList.isEmpty()) {
            throw new FindLinkException();
        }
        Chat chat = chatList.getFirst();

        List<Link> linkList = chatLinkDao.findAllLinks(tgChatId);
        if (linkList == null) {
            throw new FindLinkException();
        }

        return linkList;
    }
}

