package com.batchproject.rentservice.services;

import com.batchproject.rentservice.models.taskmodels.JobExecutionLog;
import com.batchproject.rentservice.models.taskmodels.JobExecutionRepository;
import com.batchproject.rentservice.models.taskmodels.JobType;
import com.batchproject.rentservice.models.taskmodels.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@Slf4j
@AllArgsConstructor
public class SchedulingService {
    private TaskScheduler taskScheduler;
    private JobExecutionRepository jobExecutionRepository;

    private final JobLauncher jobLauncher;
    private final Job job;

    @Transactional
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
                setLogRunning(executionLog);
//                runAnotherTask();
                JobParameters jobParameters = new JobParametersBuilder()
                        .addLong("startAt", System.currentTimeMillis())
                        .toJobParameters();
                jobLauncher.run(job, jobParameters);
                setLogDone(executionLog);

            }catch (Exception e){
                log.info("job failed, exception"+e);
                setLogError(executionLog);
            }
        },date);
    }

    public void runAnotherTask(){
        for(int i=0;i<10;i++){
            System.out.println("I am running, was set by api at ");
        }
    }

    public void setLogRunning(JobExecutionLog executionLog){
        executionLog.setExecutedAt(LocalDateTime.now());
        executionLog.setTaskStatus(TaskStatus.RUNNING);
        jobExecutionRepository.save(executionLog);
    }
    public void setLogDone(JobExecutionLog executionLog){
        executionLog.setTaskStatus(TaskStatus.SUCCESS);
        jobExecutionRepository.save(executionLog);
    }
    public void setLogError(JobExecutionLog executionLog){
        executionLog.setTaskStatus(TaskStatus.FAILED);
        jobExecutionRepository.save(executionLog);
    }

}
