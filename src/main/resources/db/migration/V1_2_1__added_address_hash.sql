ALTER TABLE Address
ADD COLUMN address_hash VARCHAR(64);

ALTER TABLE Address
ADD CONSTRAINT unique_address_hash UNIQUE (address_hash);