drop table if exists member;
drop table if exists member_interest_platform;
drop table if exists creator;
drop table if exists creator_activity_platform;
drop table if exists creator_tag;
drop table if exists tag;
drop table if exists follow;
drop table if exists message;
drop table if exists short_form;
drop table if exists short_form_like;
drop table if exists short_form_bookmark;
drop table if exists board;
drop table if exists board_like;
drop table if exists board_bookmark;
drop table if exists refresh_token;
drop table if exists review;

create table member
(
    member_id          bigint      not null auto_increment,
    created_date       datetime(6),
    updated_date       datetime(6),
    auth_provider      varchar(255),
    authorization_code varchar(255),
    email              varchar(50) not null,
    nickname           varchar(255),
    password           varchar(128),
    role               varchar(255),
    username           varchar(20),
    primary key (member_id)
);


create table member_interest_platform
(
    member_id          bigint not null,
    interest_platforms varchar(255)
);


create table creator
(
    creator_id         bigint not null auto_increment,
    created_date       datetime(6),
    updated_date       datetime(6),
    avg_rating         double precision,
    description        varchar(255),
    nickname           varchar(255),
    platform_theme     varchar(255),
    platform_head_type varchar(255),
    platform_url       varchar(255),
    profile_image      varchar(255),
    review_count       integer,
    total_rating       integer,
    member_id          bigint not null,
    primary key (creator_id)
);

create table creator_tag
(
    creator_tag_id bigint not null auto_increment,
    creator_id     bigint,
    tag_id         bigint,
    primary key (creator_tag_id)
);

create table creator_activity_platform
(
    creator_id     bigint not null,
    platform_types varchar(255)
);

create table message
(
    message_id   bigint       not null auto_increment,
    created_date datetime(6),
    updated_date datetime(6),
    text         varchar(255) not null,
    receiver     bigint       not null,
    sender       bigint       not null,
    primary key (message_id)
);

create table board
(
    board_id      bigint       not null auto_increment,
    created_date  datetime(6),
    updated_date  datetime(6),
    content       text         not null,
    platform_type varchar(255),
    title         varchar(255) not null,
    creator_id    bigint       not null,
    primary key (board_id)
);

create table board_bookmark
(
    bookmark_id bigint not null auto_increment,
    board_id    bigint,
    member_id   bigint,
    primary key (bookmark_id)
);

create table board_like
(
    like_id   bigint not null auto_increment,
    board_id  bigint,
    member_id bigint,
    primary key (like_id)
);

create table short_form
(
    shorts_id      bigint           not null auto_increment,
    created_date   datetime(6),
    updated_date   datetime(6),
    content        text             not null,
    platform_type  varchar(255),
    short_form_url varchar(255)     not null,
    thumbnail_url  varchar(255),
    title          varchar(255)     not null,
    visit          bigint default 0 not null,
    creator_id     bigint           not null,
    primary key (shorts_id)
);

create table short_form_bookmark
(
    bookmark_id bigint not null auto_increment,
    member_id   bigint,
    shorts_id   bigint,
    primary key (bookmark_id)
);

create table short_form_like
(
    like_id   bigint not null auto_increment,
    member_id bigint,
    shorts_id bigint,
    primary key (like_id)
);


create table tag
(
    id           bigint       not null auto_increment,
    created_date datetime(6),
    updated_date datetime(6),
    tag_name     varchar(255) not null,
    primary key (id)
);

create table follow
(
    follow_id bigint not null auto_increment,
    source_id bigint,
    target_id bigint,
    primary key (follow_id)
);

create table review
(
    review_id  bigint        not null auto_increment,
    content    varchar(1000) not null,
    created_at datetime(6)   not null,
    rating     integer       not null,
    creator_id bigint        not null,
    member_id  bigint        not null,
    primary key (review_id)
);

create table refresh_token
(
    refresh_token varchar(255) not null,
    user_id       bigint,
    primary key (refresh_token)
);

alter table creator add constraint idx_creator_nickname unique (nickname);

alter table creator add constraint creator_member_unique unique (member_id);
alter table member add constraint member_email_unique unique (email);
alter table board_bookmark add constraint board_bookmark_board_and_member_composite_unique unique (board_id, member_id);
alter table board_like add constraint board_like_board_and_member_composite_unique unique (board_id, member_id);
alter table creator_tag add constraint creator_tag_creator_and_tag_composite_unique unique (creator_id, tag_id);
alter table follow add constraint follow_source_and_target_composite_unique unique (source_id, target_id);
alter table short_form_bookmark add constraint short_form_bookmark_short_form_and_member_composite_unique unique (shorts_id, member_id);
alter table short_form_like add constraint short_form_like_short_form_and_member_composite_unique unique (shorts_id, member_id);

alter table board_like add constraint fk_board_like_to_board foreign key (board_id) references board (board_id);
alter table board_like add constraint fk_board_like_to_member foreign key (member_id) references member (member_id);
alter table board_bookmark add constraint fk_board_bookmark_to_board foreign key (board_id) references board (board_id);
alter table board_bookmark add constraint fk_board_bookmark_to_member foreign key (member_id) references member (member_id);
alter table short_form_like add constraint fk_short_form_to_board foreign key (shorts_id) references short_form (shorts_id);
alter table short_form_like add constraint fk_short_form_like_to_member foreign key (member_id) references member (member_id);
alter table short_form_bookmark add constraint fk_short_form_bookmark_to_member foreign key (member_id) references member (member_id);
alter table short_form_bookmark add constraint fk_short_form_bookmark_to_short_form foreign key (shorts_id) references short_form (shorts_id);
alter table creator_activity_platform add constraint fk_activity_platform_to_creator foreign key (creator_id) references creator (creator_id);
alter table member_interest_platform add constraint fk_interest_platform_to_member foreign key (member_id) references member (member_id);