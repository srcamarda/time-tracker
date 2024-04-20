CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(20) NOT NULL,
                       name VARCHAR(20),
                       cpf VARCHAR(11),
                       plan VARCHAR(20),
                       role VARCHAR(20),
                       active BOOLEAN
);