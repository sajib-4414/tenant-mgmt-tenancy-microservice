-- V2__update_amount_column.sql
-- Migration to change the data type of the 'amount' column from int to double

ALTER TABLE rent
ALTER COLUMN amount TYPE double precision;

