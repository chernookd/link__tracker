package edu.java.service.serviceDao.jdbc;

import edu.java.controller.exception.DeleteLinkException;
import edu.java.controller.exception.FindLinkException;
import edu.java.domain.dto.Chat;
import edu.java.domain.dto.Link;
import edu.java.domain.jdbc.ChatDaoJdbc;
import edu.java.domain.jdbc.ChatLinkDaoJdbc;
import edu.java.domain.jdbc.LinkDaoJdbc;
import edu.java.service.serviceDao.LinkService;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LinkServiceJdbc implements LinkService {

    private final LinkDaoJdbc linkDao;
    private final ChatDaoJdbc chatDaoJdbc;
    private final ChatLinkDaoJdbc chatLinkDaoJdbc;

    @Override
    public Link add(long tgChatId, URI url) {
        Chat chat = chatDaoJdbc.findById(tgChatId).getFirst();
        if (chat == null) {
            chatDaoJdbc.add(tgChatId);
        }

        linkDao.add(url);
        Link link = linkDao.findByUri(url);
        long linkId = link.getId();
        chatLinkDaoJdbc.add(tgChatId, linkId);

        return link;
    }

    @Override
    public Link remove(long tgChatId, URI url) throws DeleteLinkException {
        Chat chat = chatDaoJdbc.findById(tgChatId).getFirst();
        Link link = linkDao.findByUri(url);
        if (chat == null || link == null) {
            throw new DeleteLinkException();
        }

        linkDao.remove(url);
        chatLinkDaoJdbc.remove(chat.getId(), link.getId());

        return link;
    }

    @Override
    public Collection<Link> listAll(long tgChatId) throws FindLinkException {
        List<Chat> chatList = chatDaoJdbc.findById(tgChatId);
        if (chatList == null || chatList.isEmpty()) {
            throw new FindLinkException();
        }
        Chat chat = chatList.getFirst();

        List<Link> linkList = chatLinkDaoJdbc.findAllLinks(tgChatId);
        if (linkList == null) {
            throw new FindLinkException();
        }

        return linkList;
    }
}

