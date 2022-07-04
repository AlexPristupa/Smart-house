create table smarthouse.file_system_node
(
    id          bigserial primary key not null,
    created_at  timestamp,
    updated_at  timestamp,
    name        text                  not null,
    node_type   text                  not null,
    fk_file     bigint,
    fk_facility bigint                not null,
    fk_parent   bigint,
    constraint file_system_node__file_fkey
        foreign key (fk_file)
            references smarthouse.file (id) match simple
            on update no action
            on delete set null,
    constraint file_system_node__facility_fkey
        foreign key (fk_facility)
            references smarthouse.facility (id) match simple
            on update no action
            on delete cascade,
    constraint file_system_node__parent_fkey
        foreign key (fk_parent)
            references smarthouse.file_system_node (id) match simple
            on update no action
            on delete cascade
);

create unique index on smarthouse.file_system_node(name, coalesce(fk_parent, 0));

drop table smarthouse.document;
drop table smarthouse.document_group;

