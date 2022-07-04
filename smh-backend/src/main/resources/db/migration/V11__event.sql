create table smarthouse.user_event
(
    id               bigserial primary key not null,
    created_at       timestamp,
    updated_at       timestamp,
    facility_id      bigint not null,
    payload          jsonb not null,
    type             text not null,

    constraint user_event__facility_fkey foreign key (facility_id)
        references smarthouse.facility (id) match simple
        on update no action
        on delete no action
);

create table smarthouse.user_event_meta
(
    fk_entity bigint  not null,
    fk_user   bigint  not null,
    read      boolean not null default false,
    deleted   boolean not null default false,
    primary key (fk_entity, fk_user),
    constraint user_event__event_fkey foreign key (fk_entity)
        references smarthouse.user_event (id) match simple
        on update no action
        on delete no action,
    constraint user_event__user_fkey foreign key (fk_user)
        references smarthouse.user (id) match simple
        on update no action
        on delete no action
);