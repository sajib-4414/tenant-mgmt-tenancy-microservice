ALTER TABLE rent
RENAME COLUMN tenant_profile TO tenant_profile_id;

ALTER TABLE rent
ALTER COLUMN tenant_profile_id TYPE bigint USING tenant_profile_id::bigint;
