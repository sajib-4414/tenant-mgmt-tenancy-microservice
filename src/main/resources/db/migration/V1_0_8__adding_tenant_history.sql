-- V2__Create_TenantProfile.sql
CREATE TABLE tenant_profile (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    phone_no VARCHAR(50),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
);

CREATE TABLE tenant_history (
    id SERIAL PRIMARY KEY,
    tenant_profile_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    is_current_tenant BOOLEAN NOT NULL,
    housing_building_stayed BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    CONSTRAINT fk_tenant_profile FOREIGN KEY (tenant_profile_id) REFERENCES tenant_profile(id),
    CONSTRAINT fk_housing_building FOREIGN KEY (housing_building_stayed) REFERENCES housing_building(id)
);
