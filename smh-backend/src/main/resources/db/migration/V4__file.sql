create table smarthouse.file
(
    id             bigserial primary key not null,
    created_at     timestamp,
    updated_at     timestamp,
    name           text                  not null,
    content_id     uuid unique,
    content_length bigint,
    mime_type      text                  not null,
    fk_preview     bigint,
    fk_owner       bigint,
    constraint file__preview_fkey
        foreign key (fk_preview)
            references smarthouse.file (id) match simple
            on update no action
            on delete set null,
    constraint file__owner_fkey
        foreign key (fk_owner)
            references smarthouse.user (id) match simple
            on update no action
            on delete cascade
);

alter table smarthouse.user
    add column fk_image bigint,
    add constraint user__image_fk foreign key (fk_image)
        references smarthouse.file (id) match simple
        on update no action
        on delete set null;
