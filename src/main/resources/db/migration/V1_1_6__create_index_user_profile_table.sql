-- V2__add_index_to_user_profile.sql

-- Add an index to the 'keycloak_user_id' column in the 'user_profile' table
CREATE INDEX idx_keycloak_user_id ON user_profile (keycloak_user_id);


