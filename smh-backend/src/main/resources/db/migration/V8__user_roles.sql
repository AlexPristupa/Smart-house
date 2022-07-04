update smarthouse.user set role = 'WRITER' where role = 'USER';
update smarthouse.user set role = 'ROOT' where role = 'ADMIN';

alter table smarthouse.file drop column fk_owner;
alter table smarthouse.facility drop column fk_owner;
alter table smarthouse.user rename column fk_image to fk_photo;
alter table smarthouse.user rename constraint user__image_fk to user__photo_fk;
