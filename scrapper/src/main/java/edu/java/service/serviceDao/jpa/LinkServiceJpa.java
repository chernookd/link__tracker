package edu.java.service.serviceDao.jpa;

import edu.java.controller.exception.AddChatException;
import edu.java.controller.exception.AddLinkException;
import edu.java.controller.exception.DeleteChatException;
import edu.java.controller.exception.DeleteLinkException;
import edu.java.controller.exception.FindLinkException;
import edu.java.domain.dto.Link;
import edu.java.domain.jpa.ChatDaoJpa;
import edu.java.domain.jpa.ChatLinkDaoJpa;
import edu.java.domain.jpa.LinkDaoJpa;
import edu.java.domain.jpa.entity.ChatEntity;
import edu.java.domain.jpa.entity.ChatLinkEntity;
import edu.java.domain.jpa.entity.LinkEntity;
import edu.java.service.serviceDao.LinkService;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class LinkServiceJpa implements LinkService {

    private final LinkDaoJpa linkDaoJpa;
    private final ChatDaoJpa chatDaoJpa;
    private final ChatLinkDaoJpa chatLinkDaoJpa;

    @Override
    @Transactional
    public Link add(long tgChatId, URI url) {
        ChatEntity chatEntity = chatDaoJpa.findById(tgChatId).orElseThrow(AddChatException::new);
        Optional<LinkEntity> linkEntityOptional = linkDaoJpa.findByUrl(url.toString());
        LinkEntity linkEntity;
        ChatLinkEntity chatLinkEntity;

        linkEntity = linkEntityOptional.orElseGet(() -> new LinkEntity(url));

        linkDaoJpa.save(linkEntity);

        if (chatLinkDaoJpa.existsByChatAndLink(chatEntity, linkEntity)) {
            throw new AddLinkException();
        } else {
            chatLinkEntity = new ChatLinkEntity(chatEntity, linkEntity);
        }
        chatLinkDaoJpa.save(chatLinkEntity);

        return new Link(linkEntity.getId(), URI.create(linkEntity.getUrl()), linkEntity.getUpdateTime());
    }

    @Override
    @Transactional
    public Link remove(long tgChatId, URI url) throws Exception {
        ChatEntity chat = chatLinkDaoJpa.findById(tgChatId).orElseThrow(DeleteChatException::new).getChat();
        Optional<LinkEntity> linkEntityOptional = linkDaoJpa.findByUrl(url.toString());
        LinkEntity link;

        if (linkEntityOptional.isPresent()) {
            link = linkEntityOptional.get();
        } else {
            throw new DeleteLinkException();
        }

        Long remove = chatLinkDaoJpa.deleteByChatAndLink(chat, link);
        if (remove == 0) {
            throw new DeleteLinkException();
        }
        if (chatLinkDaoJpa.findAllByLink(link).isEmpty()) {
            linkDaoJpa.delete(link);
        }

        return new Link(link.getId(), URI.create(link.getUrl()), link.getUpdateTime());
    }

    @Override
    @Transactional
    public Collection<Link> listAll(long tgChatId) throws FindLinkException {
        ChatEntity chat = chatDaoJpa.findById(tgChatId).orElseThrow(FindLinkException::new);

        return chatLinkDaoJpa.findAllByChat(chat).stream()
            .map(chatLinkEntity -> {
                LinkEntity linkEntity = chatLinkEntity.getLink();
                return new Link(linkEntity.getId(), URI.create(linkEntity.getUrl()), linkEntity.getUpdateTime());
            })
            .collect(Collectors.toList());
    }
}
