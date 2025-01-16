ALTER TABLE tenant_profile
ADD COLUMN  keycloak_user_id VARCHAR(255);

CREATE UNIQUE INDEX idx_keycloak_user_id_tenant_profile ON tenant_profile (keycloak_user_id);
