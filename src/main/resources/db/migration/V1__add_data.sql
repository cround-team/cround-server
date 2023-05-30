insert into member (auth_provider, email, nickname, username, password, role)
values ('LOCAL', 'test@test.com', '테스터', '테스터', 'tester', 'USER');
insert into creator (description, member_id, platform_activity_name, platform_theme, platform_name, platform_url, profile_image)
values ('QA', 1, 'QA', 'QA', 'QA', 'QA', null);
insert into tag (tag_name) values ('QA');
insert into creator_tag (creator_id, tag_id) values (1, 1);
insert into creator_platform (creator_id, platform_name) values (1, 'QA');

insert into member (auth_provider, email, nickname, username, password, role)
values ('LOCAL', 'cround@cround.com', '크라운드', '크라운드', 'cround', 'USER');
insert into creator (description, member_id, platform_activity_name, platform_theme, platform_name, platform_url, profile_image)
values ('크라운드', 2, '크라운더', 'ccrroouunndd', 'youtube', 'QA', null);
insert into tag (tag_name) values ('cround');
insert into creator_tag (creator_id, tag_id) values (2, 2);
insert into creator_platform (creator_id, platform_name) values (2, 'youtube');
