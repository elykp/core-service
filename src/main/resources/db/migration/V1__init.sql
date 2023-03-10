drop table if exists asset_entity cascade;
drop table if exists photo_entity cascade;
drop table if exists photo_tags cascade;
drop table if exists tag_entity cascade;
drop sequence if exists tag_entity_seq;
create sequence tag_entity_seq start with 1 increment by 50;

create table asset_entity
(
    id             bigserial   not null,
    created_at     bigint,
    height         integer     not null,
    owner_id       varchar(36) not null,
    responsive_key varchar(20) not null,
    url            varchar(255) default '',
    width          integer     not null,
    photo_id       varchar(8),
    primary key (id)
);

create table photo_entity
(
    id         varchar(8)   not null,
    blurhash   varchar(30)  not null,
    created_at bigint,
    creator_id varchar(36)  not null,
    is_deleted boolean default false,
    like_count integer default 0,
    nsfw       boolean default false,
    title      varchar(255) not null,
    primary key (id)
);

create table photo_tags
(
    photo_id varchar(8) not null,
    tag_id   bigint     not null,
    primary key (photo_id, tag_id)
);

create table tag_entity
(
    id         bigint       not null,
    created_at bigint,
    name       varchar(100) not null,
    primary key (id)
);

alter table if exists tag_entity
    add constraint UNIQUE_NAME_CONSTRAINT unique (name);

alter table if exists asset_entity
    add constraint FK_photo_asset
        foreign key (photo_id)
            references photo_entity;

alter table if exists photo_tags
    add constraint FK_tag_photo_tags
        foreign key (tag_id)
            references tag_entity;

alter table if exists photo_tags
    add constraint FK_photo_photo_tags
        foreign key (photo_id)
            references photo_entity;