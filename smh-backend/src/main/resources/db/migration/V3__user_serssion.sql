create table if not exists smarthouse.session
(
    id         bigserial primary key not null,
    created_at timestamp             not null,
    updated_at timestamp,
    token_uuid text                  not null,
    expire_at  timestamp             not null,
    browser    text,
    ip         text,
    os         text,
    online     boolean               not null default false,
    fk_user    bigint                not null,
    constraint session__user_fkey foreign key (fk_user)
        references smarthouse.user (id) match simple
        on update no action
        on delete no action
);