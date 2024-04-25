CREATE TABLE users(
    id           bigserial primary key,
    username     varchar(30) not null,
    name         varchar(100),
    email        varchar(50),
    cpf          varchar(14),
    role         varchar(20),
    addr_zip     varchar(10),
    addr_country varchar(60),
    addr_state   varchar(60),
    addr_city    varchar(60),
    addr_street  varchar(100),
    addr_number  integer,
    active       boolean
);

create index users_username on users (username);
create index users_active on users (active);

create table tasks(
    id          bigserial primary key,
    title       varchar(50) not null,
    description varchar(200),
    tag         varchar(30),
    id_user     bigint references users (id),
    start_time  timestamp,
    end_time    timestamp,
    active      boolean
);

create index tasks_tag on tasks (tag);
create index tasks_active on tasks (active);

create table projects(
    id          bigserial primary key,
    title       varchar(50) not null,
    description varchar(200),
    start_time  timestamp,
    end_time    timestamp,
    active      boolean
);

create index projects_active on projects (active);

create table project_users(
    id_project bigint references projects (id),
    id_user    bigint references users (id)
);

create table project_tasks(
    id_project bigint references projects (id),
    id_task    bigint references tasks (id)
);

create table project_tags(
    id_project bigint references projects (id),
    tag        varchar(30)
);