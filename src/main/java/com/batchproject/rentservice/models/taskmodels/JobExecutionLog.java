package com.batchproject.rentservice.models.taskmodels;

import com.batchproject.rentservice.models.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "job_execution_log")
public class JobExecutionLog extends BaseEntity {
    @Column(name = "job_name")
    private String jobName;

    @Column(name = "instructed_at")
    private LocalDateTime instructedAt;//if api sets a job to be run at set time

    //the time this job was set by API call
    // for non specific future's scheduled job, like with fixed rate, fixed delay, cron, this field will be filled at the time of job run.
    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Column(name = "executed_at")
    private LocalDateTime executedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    private TaskStatus taskStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type")
    private JobType jobType;

}

//lets brainstorm
/*
i want to scheulde a job by API, at that moment, there will be no batch job Id, only job execution log with status=scheduled
we will use task scheduler to set to run a method, when that method runs, that time if we can have the
 */
