package com.batchproject.rentservice.services;

import com.batchproject.rentservice.models.taskmodels.JobExecutionLog;
import com.batchproject.rentservice.models.taskmodels.JobExecutionRepository;
import com.batchproject.rentservice.models.taskmodels.JobType;
import com.batchproject.rentservice.models.taskmodels.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Slf4j
@AllArgsConstructor
public class SchedulingService {
    private TaskScheduler taskScheduler;
    private JobExecutionRepository jobExecutionRepository;

    public void processRent(Integer month, Integer year, @NotNull LocalDateTime runAt) {

        JobExecutionLog executionLog = JobExecutionLog.builder()
                .scheduledAt(runAt)
                .jobType(JobType.MANUALLY_SCHEDULED)
                .jobName("processRent")
                .taskStatus(TaskStatus.SCHEDULED)
                .instructedAt(LocalDateTime.now())
                .build();
        jobExecutionRepository.save(executionLog);

        Date date = Date.from(runAt.atZone(ZoneId.systemDefault()).toInstant());
        //for now just do a lambda method
        taskScheduler.schedule(()->{
            try{
                executionLog.setExecutedAt(LocalDateTime.now());
                executionLog.setTaskStatus(TaskStatus.RUNNING);
                jobExecutionRepository.save(executionLog);
                runAnotherTask();
                executionLog.setTaskStatus(TaskStatus.SUCCESS);
                jobExecutionRepository.save(executionLog);

            }catch (Exception e){
                log.info("job failed, exception"+e);
                executionLog.setTaskStatus(TaskStatus.FAILED);
                jobExecutionRepository.save(executionLog);
            }
        },date);
    }

    public void runAnotherTask(){
        for(int i=0;i<10;i++){
            System.out.println("I am running, was set by api at ");
        }
    }
}
