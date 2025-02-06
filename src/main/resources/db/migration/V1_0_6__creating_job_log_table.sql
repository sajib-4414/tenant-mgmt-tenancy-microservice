CREATE TABLE job_execution_log (
    id BIGSERIAL NOT NULL PRIMARY KEY,               -- BaseEntity: id field
    job_name VARCHAR(255) NOT NULL,                      -- JobExecutionLog: jobName
    instructed_at TIMESTAMP,                             -- JobExecutionLog: instructedAt
    scheduled_at TIMESTAMP,                              -- JobExecutionLog: scheduledAt
    executed_at TIMESTAMP,                               -- JobExecutionLog: executedAt
    task_status VARCHAR(255) NOT NULL,                   -- JobExecutionLog: taskStatus (Enum will be stored as string)
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- BaseEntity: createdAt
    updated_at TIMESTAMP,                                -- BaseEntity: updatedAt
    created_by VARCHAR(255),                             -- BaseEntity: createdBy
    updated_by VARCHAR(255),                             -- BaseEntity: updatedBy
    CONSTRAINT task_status_enum CHECK (task_status IN ('SCHEDULED', 'RUNNING', 'SUCCESS', 'FAILED'))  -- Ensure taskStatus is one of the enum values
);

-- Optionally, you can create an index on frequently queried fields for performance improvement
CREATE INDEX idx_job_execution_log_job_name ON job_execution_log (job_name);