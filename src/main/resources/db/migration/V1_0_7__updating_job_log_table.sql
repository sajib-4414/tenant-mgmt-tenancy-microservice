-- V2__Add_JobType_Column.sql

-- Add the new column with a default value to avoid issues with existing records
ALTER TABLE job_execution_log
ADD COLUMN job_type VARCHAR(50) NOT NULL DEFAULT 'MANUALLY_SCHEDULED';

-- Ensure that the column only accepts valid job types
ALTER TABLE job_execution_log
ADD CONSTRAINT job_type_enum CHECK (job_type IN ('CRONJOB', 'MANUALLY_SCHEDULED', 'FIXED_RATE', 'FIXED_DELAY', 'ONE_TIME', 'RECURRING'));
