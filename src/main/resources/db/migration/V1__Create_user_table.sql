CREATE TABLE user
(
    id           INTEGER AUTO_INCREMENT PRIMARY KEY,
    account_id   VARCHAR(100),
    name         VARCHAR(50),
    token        VARCHAR(36),
    gmt_create   BIGINT,
    gmt_modified BIGINT,
    bio          VARCHAR(256),
    email        VARCHAR(50),
    login        VARCHAR(50),
    avatarurl    VARCHAR(256)
);