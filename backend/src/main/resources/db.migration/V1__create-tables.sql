CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(30) NOT NULL,
    name          VARCHAR(100),
    email         VARCHAR(50),
    cpf           VARCHAR(14),
    role          VARCHAR(20),
    addr_zip      VARCHAR(10),
    addr_country  VARCHAR(60),
    addr_state    VARCHAR(60),
    addr_city     VARCHAR(60),
    addr_street   VARCHAR(100),
    addr_district VARCHAR(60),
    addr_number   INTEGER,
    password      VARCHAR(100),
    active        BOOLEAN
);

CREATE UNIQUE INDEX users_username ON users (username);
CREATE INDEX users_active ON users (active);

CREATE TABLE projects
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(50) NOT NULL,
    description VARCHAR(200),
    start_time  TIMESTAMP,
    end_time    TIMESTAMP,
    active      BOOLEAN
);

CREATE INDEX projects_active ON projects (active);

CREATE TABLE tasks
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(50) NOT NULL,
    description VARCHAR(200),
    tag         VARCHAR(30),
    id_user     BIGINT REFERENCES users (id),
    start_time  TIMESTAMP,
    end_time    TIMESTAMP,
    active      BOOLEAN,
    id_project  BIGINT REFERENCES projects (id)
);

CREATE INDEX tasks_tag ON tasks (tag);
CREATE INDEX tasks_active ON tasks (active);

CREATE TABLE project_users
(
    id_project BIGINT REFERENCES projects (id),
    id_user    BIGINT REFERENCES users (id),
    PRIMARY KEY (id_project, id_user)
);

CREATE TABLE project_tasks
(
    id_project BIGINT REFERENCES projects (id),
    id_task    BIGINT REFERENCES tasks (id),
    PRIMARY KEY (id_project, id_task)
);

CREATE TABLE project_tags
(
    id_project BIGINT REFERENCES projects (id),
    tag        VARCHAR(30),
    PRIMARY KEY (id_project, tag)
);

CREATE TABLE user_roles
(
    username  VARCHAR(30) REFERENCES users (username),
    role VARCHAR(30) NOT NULL
);

CREATE UNIQUE INDEX username_roles ON user_roles (username, role);