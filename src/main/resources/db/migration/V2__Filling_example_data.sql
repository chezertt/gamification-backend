insert into companies (id, name)
values (1, 'Компания 1'),
       (2, 'Компания 2');
select setval('companies_id_seq', 2);

insert into users (id, email, password, role, company_id, registered_at)
values (1, 'player1@example.com', '$2a$10$33Mvze0qkF9IBCDtcAUX8.c8TuF.mjdKpLMHyjcZJFzGmKUnJFfEK', 'ROLE_PLAYER', 1, now() - interval '20 day'),
       (2, 'player2@example.com', '$2a$10$33Mvze0qkF9IBCDtcAUX8.c8TuF.mjdKpLMHyjcZJFzGmKUnJFfEK', 'ROLE_PLAYER', 1, now() - interval '20 day'),
       (3, 'player3@example.com', '$2a$10$33Mvze0qkF9IBCDtcAUX8.c8TuF.mjdKpLMHyjcZJFzGmKUnJFfEK', 'ROLE_PLAYER', 1, now() - interval '20 day'),
       (4, 'player4@example.com', '$2a$10$33Mvze0qkF9IBCDtcAUX8.c8TuF.mjdKpLMHyjcZJFzGmKUnJFfEK', 'ROLE_PLAYER', 1, now() - interval '20 day'),
       (5, 'company1@example.com', '$2a$10$33Mvze0qkF9IBCDtcAUX8.c8TuF.mjdKpLMHyjcZJFzGmKUnJFfEK', 'ROLE_COMPANY', 1, now() - interval '20 day'),
       (6, 'company2@example.com', '$2a$10$33Mvze0qkF9IBCDtcAUX8.c8TuF.mjdKpLMHyjcZJFzGmKUnJFfEK', 'ROLE_COMPANY', 2, now() - interval '20 day');
select setval('users_id_seq', 6);

insert into invitations(id, company_id, player_email, player_first_name, player_last_name, player_title, sent_at)
values (1, 1, 'ivan.kyznetsov@example.com', 'Иван', 'Кузнецов', 'Дизайнер', now() - interval '20 day'),
       (2, 1, 'olga.sokolova@example.com', 'Ольга', 'Соколова', 'Тестировщик', now() - interval '20 day');
select setval('invitations_id_seq', 2);

insert into players(id, user_id, first_name, last_name, title)
    values (1, 1, 'Иван', 'Иванов', 'Маркетолог'),
           (2, 2, 'Маргарита', 'Смирнова', 'Разработчик'),
           (3, 3, 'Владимир', 'Васильев', 'Тестировщик'),
           (4, 4, 'Алла', 'Петрова', 'Дизайнер');
select setval('players_id_seq', 4);

insert into bonus_types(id, company_id, name, created_at)
values (1, 1, 'Достижение', now() - interval '20 day'),
       (2, 1, 'Приз в реальном мире', now() - interval '20 day');
select setval('bonus_types_id_seq', 2);

insert into bonuses(id, company_id, type_id, name, created_at)
values (1, 1, 2, 'Премия 30% от ЗП', now() - interval '20 day'),
       (2, 1, 2, 'Премия 60% от ЗП', now() - interval '20 day'),
       (3, 1, 1, 'Звание Сотрудник года', now() - interval '20 day'),
       (4, 1, 2, 'Сертификат на горнолыжный курорт', now() - interval '20 day'),
       (6, 1, 2, 'Дополнительный день отпуска', now() - interval '20 day'),
       (7, 1, 2, 'Корпоративный мерч', now() - interval '20 day');
select setval('bonuses_id_seq', 7);

insert into event_types(id, company_id, name, created_at)
values (1, 1, 'Задание', now() - interval '20 day'),
       (2, 1, 'Мероприятие', now() - interval '20 day'),
       (3, 1, 'Соревнование', now() - interval '20 day');
select setval('event_types_id_seq', 3);

insert into events(id, company_id, type_id, name, points, coins, created_at)
values (1, 1, 1, 'Выполнить цели за весенний квартал 70+%', 300, 200, now() - interval '20 day'),
       (2, 1, 3, 'Соревнование на лучшего сотрудника 2024 года', 500, 0, now() - interval '20 day'),
       (3, 1, 2, 'Корпоратив на природе', 100, 100, now() - interval '20 day');
select setval('events_id_seq', 3);

insert into events_bonuses(event_id, bonus_id)
values (1, 1),
       (2, 3),
       (2, 4),
       (2, 2);

insert into shop_items(id, company_id, bonus_id, price_in_coins, created_at)
values (1, 1, 7, 250, now() - interval '20 day'),
       (2, 1, 4, 1000, now() - interval '20 day'),
       (3, 1, 6, 1500, now() - interval '20 day');
select setval('shop_items_id_seq', 3);

insert into statistics(id, company_id, completed_events, obtained_bonuses, created_at)
values (1, 1, 0, 0, current_date - interval '28 day'),
       (2, 1, 0, 0, current_date - interval '27 day'),
       (3, 1, 0, 0, current_date - interval '26 day'),
       (4, 1, 0, 0, current_date - interval '25 day'),
       (5, 1, 1, 1, current_date - interval '24 day'),
       (6, 1, 1, 2, current_date - interval '23 day'),
       (7, 1, 2, 3, current_date - interval '22 day'),
       (8, 1, 3, 4, current_date - interval '21 day'),
       (9, 1, 4, 4, current_date - interval '20 day');
select setval('statistics_id_seq', 9);