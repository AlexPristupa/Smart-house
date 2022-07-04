alter table service_work_type add column pinned boolean not null default false;
update service_work_type set pinned = true where id in (1, 2, 3, 4);
