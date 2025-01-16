-- Migration for adding updated_by to housing_building
ALTER TABLE housing_building
ADD COLUMN updated_by VARCHAR(255);

-- Migration for adding updated_by to maintenance
ALTER TABLE maintenance
ADD COLUMN updated_by VARCHAR(255);

-- Migration for adding audit fields to metadata
ALTER TABLE metadata
ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);

-- Migration for adding created_by and updated_by to rent
ALTER TABLE rent
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN updated_by VARCHAR(255);

-- Migration for adding updated_by to suite
ALTER TABLE suite
ADD COLUMN updated_by VARCHAR(255);
