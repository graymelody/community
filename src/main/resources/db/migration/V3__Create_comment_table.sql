create table comment
(
    id bigint auto_increment primary key,
    parent_id bigint not null,
    type int not null,
    commentator int not null,
    gmt_create long not null,
    gmt_modified long not null,
    like_count bigint default 0,
    content varchar(1024)
);