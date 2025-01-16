-- V2__create_tenancy_table.sql
CREATE TABLE tenancy (
    id BIGSERIAL PRIMARY KEY, -- Auto-incrementing ID for tenancy
    suite_id BIGINT NOT NULL, -- Foreign key to suite table
    tenant_profile_id BIGINT NOT NULL, -- Foreign key to tenant_profile table
    start_date DATE NOT NULL,
    end_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Optional audit field
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    CONSTRAINT fk_suite FOREIGN KEY (suite_id) REFERENCES suite(id),
    CONSTRAINT fk_tenant_profile FOREIGN KEY (tenant_profile_id) REFERENCES tenant_profile(id)
);
