-- V1__create_rent_table.sql
CREATE TABLE rent (
    id BIGSERIAL PRIMARY KEY, -- Auto-incrementing ID for PostgreSQL
    amount INTEGER NOT NULL,
    due_date DATE NOT NULL,
    paid_date DATE,
    status VARCHAR(255),
    tenant_profile JSONB, -- Assuming `tenant_profile` is stored as JSON
    suite_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Assuming audit fields
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_suite FOREIGN KEY (suite_id) REFERENCES suite(id)
);
