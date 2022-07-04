create table smarthouse.facility
(
    id          bigserial primary key not null,
    created_at  timestamp,
    updated_at  timestamp,
    name        text                  not null,
    description text,
    address     text                  not null,
    fk_avatar   bigint,
    fk_owner    bigint,
    constraint facility__avatar_fkey
        foreign key (fk_avatar)
            references smarthouse.file (id) match simple
            on update no action
            on delete set null,
    constraint facility__owner_fkey
        foreign key (fk_owner)
            references smarthouse.user (id) match simple
            on update no action
            on delete set null
);

create table smarthouse.facility_images
(
    fk_facility bigint not null,
    fk_image    bigint not null,
    constraint facility_images_pkey primary key (fk_facility, fk_image),
    constraint facility_images__facility_fkey
        foreign key (fk_facility)
            references smarthouse.facility (id) match simple
            on update no action
            on delete cascade,
    constraint facility_images__image_fkey
        foreign key (fk_image)
            references smarthouse.file (id) match simple
            on update no action
            on delete cascade
);

create table smarthouse.service_work_type
(
    id          bigserial primary key not null,
    created_at  timestamp,
    updated_at  timestamp,
    name        text                  not null unique,
    description text,
    icon        text
);

create table smarthouse.service_work
(
    id              bigserial primary key not null,
    created_at      timestamp,
    updated_at      timestamp,
    name            text                  not null,
    description     text,
    start_time      timestamp             not null,
    finish_time     timestamp             not null,
    resolution_time timestamp,
    status          text                  not null,
    resolution      text                  not null,
    fk_type         bigint,
    fk_facility     bigint                not null,
    constraint service_work__facility_fkey
        foreign key (fk_facility)
            references smarthouse.facility (id) match simple
            on update no action
            on delete cascade,
    constraint service_work__type_fkey
        foreign key (fk_type)
            references smarthouse.service_work_type (id) match simple
            on update no action
            on delete set null
);

create table smarthouse.hardware
(
    id            bigserial primary key not null,
    created_at    timestamp,
    updated_at    timestamp,
    name          text                  not null,
    description   text,
    model         text                  not null,
    serial_number text                  not null,
    installed_at  timestamp             not null,
    expires_at    timestamp,
    installer     text,
    fk_avatar     bigint,
    fk_facility   bigint                not null,
    constraint hardware__avatar_fkey
        foreign key (fk_avatar)
            references smarthouse.file (id) match simple
            on update no action
            on delete set null,
    constraint hardware__facility_fkey
        foreign key (fk_facility)
            references smarthouse.facility (id) match simple
            on update no action
            on delete cascade
);

create table smarthouse.hardware_images
(
    fk_hardware bigint not null,
    fk_image    bigint not null,
    constraint hardware_images_pkey primary key (fk_hardware, fk_image),
    constraint hardware_images__hardware_fkey
        foreign key (fk_hardware)
            references smarthouse.hardware (id) match simple
            on update no action
            on delete cascade,
    constraint hardware_images__image_fkey
        foreign key (fk_image)
            references smarthouse.file (id) match simple
            on update no action
            on delete cascade
);

create table smarthouse.document_group
(
    id          bigserial primary key not null,
    created_at  timestamp,
    updated_at  timestamp,
    name        text                  not null unique,
    description text
);

create table smarthouse.document
(
    id                bigserial primary key not null,
    created_at        timestamp,
    updated_at        timestamp,
    name              text                  not null,
    description       text,
    fk_file           bigint                not null,
    fk_document_group bigint,
    fk_facility       bigint                not null,
    constraint document__file_fkey
        foreign key (fk_file)
            references smarthouse.file (id) match simple
            on update no action
            on delete cascade,
    constraint document__document_group_fkey
        foreign key (fk_document_group)
            references smarthouse.document_group (id) match simple
            on update no action
            on delete set null,
    constraint document__facility_fkey
        foreign key (fk_facility)
            references smarthouse.facility (id) match simple
            on update no action
            on delete cascade
);

insert into smarthouse.service_work_type(created_at, name)
values (now(), 'Обслуживание'),
       (now(), 'Платеж'),
       (now(), 'Передача показаний'),
       (now(), 'Мастер');
