-- V2_1_2__create_work_order_table.sql
CREATE TABLE work_order (
    id BIGSERIAL PRIMARY KEY, -- Auto-increment ID
    description TEXT NOT NULL, -- Description of the work order
    completed_date DATE, -- Date when work was completed
    status VARCHAR(50) NOT NULL, -- Status of the work order
    notes TEXT, -- Additional notes
    suite_id BIGINT NOT NULL, -- Foreign key to suite table
    housing_building_id BIGINT NOT NULL, -- Foreign key to housing_building table
    maintenance_id BIGINT NOT NULL, -- Foreign key to maintenance table
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Audit field
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    CONSTRAINT fk_suite FOREIGN KEY (suite_id) REFERENCES suite(id),
    CONSTRAINT fk_housing_building FOREIGN KEY (housing_building_id) REFERENCES housing_building(id),
    CONSTRAINT fk_maintenance FOREIGN KEY (maintenance_id) REFERENCES maintenance(id)
);
