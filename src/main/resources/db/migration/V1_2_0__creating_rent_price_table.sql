CREATE TABLE rent_price (
    id BIGSERIAL PRIMARY KEY,
    suite_id bigint,
    effective_start_date DATE,
    effective_end_date DATE,
    rent_amount INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Audit field
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50)
);