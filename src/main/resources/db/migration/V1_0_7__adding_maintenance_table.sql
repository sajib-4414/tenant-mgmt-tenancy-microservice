CREATE TABLE maintenance (
    id SERIAL PRIMARY KEY,
    issue_description VARCHAR(255) NOT NULL,
    request_date DATE NOT NULL,
    priority VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(50)
);

CREATE INDEX idx_maintenance_request_date ON maintenance(request_date);
CREATE INDEX idx_maintenance_priority ON maintenance(priority);
CREATE INDEX idx_maintenance_status ON maintenance(status);
