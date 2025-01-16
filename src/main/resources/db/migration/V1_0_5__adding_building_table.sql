CREATE TABLE housing_building (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    address_id BIGINT,
    has_in_house_laundry BOOLEAN,
    possessed_on DATE,
    built_on DATE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(50),
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES address(id)
);

ALTER table metadata
ADD COLUMN id SERIAL PRIMARY KEY;
