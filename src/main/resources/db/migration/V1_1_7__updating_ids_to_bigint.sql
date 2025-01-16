alter table address
alter column id type bigint;

alter table housing_building
alter column id type bigint,
alter column address_id type bigint;

alter table maintenance
alter column id type bigint,
alter column suite_id type bigint;

alter table suite
alter column id type bigint,
alter column address_id type bigint;

alter table sysdata
alter column id type bigint;

alter table tenant_history
alter column id type bigint;

alter table tenant_profile
alter column id type bigint;


