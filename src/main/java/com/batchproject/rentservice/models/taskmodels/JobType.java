package com.batchproject.rentservice.models.taskmodels;

public enum JobType {
    CRONJOB, // Jobs scheduled using a cron expression
    MANUALLY_SCHEDULED, // Jobs scheduled via an API call with a specific time
    FIXED_RATE, // Jobs running at a fixed rate (e.g., every 10 seconds)
    FIXED_DELAY, // Jobs running after a fixed delay from the last execution
}
