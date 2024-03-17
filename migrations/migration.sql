

CREATE TABLE LINK_TABLE
(
    id   BIGSERIAL,
    url  VARCHAR(255) NOT NULL,
    update_time TIMESTAMP WITH TIME ZONE NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (url)
);


CREATE TABLE CHAT_TABLE
(
    id BIGINT,
    PRIMARY KEY (id)
);
CREATE TABLE CHAT_LINK
(
    id BIGSERIAL,
    chat_id BIGINT NOT NULL,
    link_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (chat_id) REFERENCES CHAT_TABLE(ID),
    FOREIGN KEY (link_id) REFERENCES LINK_TABLE(ID)
);
