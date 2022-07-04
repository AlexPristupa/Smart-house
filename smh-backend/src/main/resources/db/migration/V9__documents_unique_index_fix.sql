drop index smarthouse.file_system_node_name_coalesce_idx;
create unique index file_system_node_name_idx on smarthouse.file_system_node(name, fk_facility, coalesce(fk_parent, 0));

