CREATE TABLE suite (
    id SERIAL PRIMARY KEY,
    address_id BIGINT,
    built_on DATE,
    last_renovated_on DATE,
    no_of_bedrooms INTEGER,
    no_of_bathrooms INTEGER,
    have_dedicated_laundry BOOLEAN,
    floor_no INTEGER,
    building_id BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(50),
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES address(id),
    CONSTRAINT fk_building FOREIGN KEY (building_id) REFERENCES housing_building(id)
);