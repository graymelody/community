CREATE TABLE question
(
    id            INTEGER AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(50),
    description   CLOB,
    gmt_create    BIGINT,
    gmt_modified  BIGINT,
    creator       INTEGER,
    comment_count INTEGER DEFAULT 0,
    view_count    INTEGER DEFAULT 0,
    like_count    INTEGER DEFAULT 0,
    tag           VARCHAR(256)
);