package edu.java.service.serviceDao;

import edu.java.controller.exception.FindLinkException;
import edu.java.domain.dto.Link;
import java.net.URI;
import java.util.Collection;

public interface LinkService {
    Link add(long tgChatId, URI url);

    Link remove(long tgChatId, URI url) throws Exception;

    Collection<Link> listAll(long tgChatId) throws FindLinkException;
}
