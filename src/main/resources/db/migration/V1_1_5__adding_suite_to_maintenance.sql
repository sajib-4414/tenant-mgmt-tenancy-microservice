-- V2__Add_suite_id_to_maintenance.sql
-- Add suite_id column to maintenance table

ALTER TABLE maintenance
ADD COLUMN suite_id INT;

-- Add foreign key constraint for suite_id
ALTER TABLE maintenance
ADD CONSTRAINT fk_suite_id
FOREIGN KEY (suite_id)
REFERENCES suite(id)
ON DELETE SET NULL;

