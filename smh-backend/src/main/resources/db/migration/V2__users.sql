create table smarthouse.user
(
    id               bigserial primary key not null,
    created_at       timestamp,
    updated_at       timestamp,
    status           text                  not null,
    role             text                  not null,
    email            text                  not null unique,
    password         text                  not null,
    first_name       text                  not null,
    last_name        text                  not null,
    patronymic       text,
    birth_date       timestamp,
    last_activity_at timestamp
);

-- admin: ad@mi.n:admin (bcrypt, 12 rounds)
insert into smarthouse.user (created_at, status, role, email, password, first_name, last_name, birth_date)
values (now(), 'ACTIVE', 'ADMIN', 'ad@mi.n', '$2a$12$G4MKxgMGx1MkCYdAn6./o.ZIGx4jAzJjmlfkuB8sXwkpz7WVhu3B2', 'admin', 'admin', now());
