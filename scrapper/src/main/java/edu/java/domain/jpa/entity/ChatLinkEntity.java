package edu.java.domain.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CHAT_LINK", uniqueConstraints = {@UniqueConstraint(columnNames = {"chat_id", "link_id"})})
public class ChatLinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false, updatable = false)
    ChatEntity chat;

    @ManyToOne
    @JoinColumn(name = "link_id", nullable = false, updatable = false)
    LinkEntity link;

    public ChatLinkEntity(ChatEntity chat, LinkEntity link) {
        this.chat = chat;
        this.link = link;
    }

}
