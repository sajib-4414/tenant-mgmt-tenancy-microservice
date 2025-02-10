package com.batchproject.rentservice.tasks;

import com.batchproject.rentservice.models.taskmodels.JobExecutionLog;
import com.batchproject.rentservice.models.taskmodels.JobExecutionRepository;
import com.batchproject.rentservice.models.taskmodels.JobType;
import com.batchproject.rentservice.models.taskmodels.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class MyScheduledTask {
    private JobExecutionRepository jobExecutionRepository;

//    @Scheduled(fixedRate = 5000, initialDelay = 2000)
    public void runTask(){
        //first note it in the job execution repository

        System.out.println("Task executed at: " + LocalDateTime.now());
        JobExecutionLog log = JobExecutionLog.builder()
                .scheduledAt(LocalDateTime.now())
                .taskStatus(TaskStatus.SUCCESS)
                .executedAt(LocalDateTime.now())
                .instructedAt(LocalDateTime.now())
                .jobName("runTask")
                .jobType(JobType.FIXED_RATE)
                .build();
        jobExecutionRepository.save(log);
    }



}
