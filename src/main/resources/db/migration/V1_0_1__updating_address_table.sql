ALTER table address
ADD COLUMN id SERIAL PRIMARY KEY,
ADD COLUMN created_at TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP,
ADD COLUMN created_by varchar(50)