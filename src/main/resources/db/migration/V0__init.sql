drop table if exists member;
drop table if exists member_platform;
drop table if exists creator;
drop table if exists creator_platform;
drop table if exists creator_tag;
drop table if exists tag;
drop table if exists follow;
drop table if exists shorts_class;
drop table if exists shorts_like;
drop table if exists shorts_bookmark;
drop table if exists board;
drop table if exists board_like;
drop table if exists board_bookmark;
drop table if exists refresh_token;

create table member
(
    member_id     bigint      not null auto_increment primary key,
    created_date  datetime(6),
    updated_date  datetime(6),
    auth_provider varchar(255),
    email         varchar(50) not null unique,
    nickname      varchar(20) not null unique,
    password      varchar(128),
    role          varchar(255) not null,
    username      varchar(20) not null unique
);

create table member_platform
(
    member_id     bigint not null,
    platform_name varchar(255) not null
);

create table creator
(
    creator_id             bigint not null auto_increment primary key,
    created_date           datetime(6),
    updated_date           datetime(6),
    description            varchar(255) not null,
    platform_activity_name varchar(255) not null,
    platform_theme         varchar(255) not null,
    platform_name          varchar(255) not null,
    platform_url           varchar(255) not null,
    profile_image          varchar(255),
    member_id              bigint not null
);

create table creator_platform
(
    creator_id    bigint not null,
    platform_name varchar(255)
) engine = InnoDB;

create table creator_tag
(
    creator_tag_id bigint not null auto_increment,
    creator_id     bigint,
    tag_id         bigint,
    primary key (creator_tag_id)
) engine = InnoDB;

create table tag
(
    id           bigint       not null auto_increment,
    created_date datetime(6),
    updated_date datetime(6),
    tag_name     varchar(255) not null,
    primary key (id)
) engine = InnoDB;

create table follow
(
    follow_id    bigint not null auto_increment,
    created_date datetime(6),
    updated_date datetime(6),
    source_id    bigint,
    target_id    bigint,
    primary key (follow_id)
) engine = InnoDB;

create table shorts_class
(
    shorts_id     bigint       not null auto_increment,
    created_date  datetime(6),
    updated_date  datetime(6),
    content       text         not null,
    platform_name varchar(255),
    shorts_url    varchar(255) not null,
    title         varchar(255) not null,
    creator_id    bigint,
    primary key (shorts_id)
) engine = InnoDB;

create table shorts_like
(
    like_id   bigint not null auto_increment,
    member_id bigint,
    shorts_id bigint,
    primary key (like_id)
) engine = InnoDB;

create table shorts_bookmark
(
    bookmark_id bigint not null auto_increment,
    member_id   bigint,
    shorts_id   bigint,
    primary key (bookmark_id)
) engine = InnoDB;

create table board
(
    board_id      bigint       not null auto_increment,
    created_date  datetime(6),
    updated_date  datetime(6),
    content       text         not null,
    platform_name varchar(255),
    title         varchar(255) not null,
    creator_id    bigint,
    primary key (board_id)
) engine = InnoDB;

create table board_like
(
    like_id   bigint not null auto_increment,
    board_id  bigint,
    member_id bigint,
    primary key (like_id)
) engine = InnoDB;

create table board_bookmark
(
    bookmark_id bigint not null auto_increment,
    board_id    bigint,
    member_id   bigint,
    primary key (bookmark_id)
) engine = InnoDB;

create table refresh_token
(
    refresh_token varchar(255) not null,
    user_id       bigint,
    primary key (refresh_token)
) engine = InnoDB;
