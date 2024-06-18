create table companies
(
    id                     bigserial primary key,
    name                   varchar(100) not null unique,
    level_formula_constant int          not null default 5,
    level_formula_power    int          not null default 2
);

create table users
(
    id            bigserial primary key,
    company_id    bigint                   not null references companies (id),
    email         varchar(255)             not null unique,
    password      varchar(255)             not null,
    role          varchar(50)              not null,
    registered_at timestamp with time zone not null default now()
);

create table invitations
(
    id                bigserial primary key,
    company_id        bigint                   not null references companies (id),
    player_email      varchar(255)             not null,
    player_first_name varchar(35)              not null,
    player_last_name  varchar(35)              not null,
    player_title      varchar(50)              not null,
    sent_at           timestamp with time zone not null default now(),
    unique (company_id, player_email)
);

create table players
(
    id                   bigserial primary key,
    user_id              bigint      not null unique references users (id),
    first_name           varchar(35) not null,
    last_name            varchar(35) not null,
    title                varchar(50) not null,
    level                int         not null default 1,
    points               int         not null default 0,
    current_level_points int         not null default 0,
    next_level_points    int         not null default 0,
    coins                int         not null default 0
);

create table bonus_types
(
    id         bigserial primary key,
    company_id bigint                   not null references companies (id),
    name       varchar(50)              not null,
    created_at timestamp with time zone not null default now(),
    unique (company_id, name)
);

create table bonuses
(
    id         bigserial primary key,
    company_id bigint                   not null references companies (id),
    type_id    bigint references bonus_types (id),
    name       varchar(50)              not null,
    created_at timestamp with time zone not null default now(),
    unique (company_id, name)
);

create table event_types
(
    id         bigserial primary key,
    company_id bigint                   not null references companies (id),
    name       varchar(50)              not null,
    created_at timestamp with time zone not null default now(),
    unique (company_id, name)
);

create table events
(
    id           bigserial primary key,
    company_id   bigint                   not null references companies (id),
    type_id      bigint references event_types (id),
    name         varchar(50)              not null,
    description  varchar(255),
    points       int                      not null default 0,
    coins        int                      not null default 0,
    is_available boolean                  not null default true,
    created_at   timestamp with time zone not null default now(),
    unique (company_id, name)
);

create table events_bonuses
(
    event_id bigint references events (id),
    bonus_id bigint references bonuses (id),
    primary key (event_id, bonus_id)
);

create table players_completed_events
(
    player_id          bigint references players (id),
    completed_event_id bigint references events (id),
    completed_at       timestamp with time zone not null default now(),
    primary key (player_id, completed_event_id)
);

create table players_obtained_bonuses
(
    player_id         bigint references players (id),
    obtained_bonus_id bigint references bonuses (id),
    obtained_at       timestamp with time zone not null default now(),
    primary key (player_id, obtained_bonus_id, obtained_at)
);

create table shop_items
(
    id             bigserial primary key,
    company_id     bigint                   not null references companies (id),
    bonus_id       bigint                   not null unique references bonuses (id),
    price_in_coins int                      not null,
    is_available   boolean                  not null default true,
    created_at     timestamp with time zone not null default now()
);

create table statistics
(
    id               bigserial primary key,
    company_id       bigint not null references companies (id),
    completed_events int    not null default 0,
    obtained_bonuses int    not null default 0,
    created_at       date
);
