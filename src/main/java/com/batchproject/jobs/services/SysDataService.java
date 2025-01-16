package com.batchproject.jobs.services;

import com.batchproject.jobs.models.SysDataRepository;
import com.batchproject.jobs.models.SysData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class SysDataService {
    private final SysDataRepository sysDataRepository;

    @Async
    public CompletableFuture<List<SysData>> getAllSysData(){
            String asyncThreadName = Thread.currentThread().getName();
            log.info("Executing repository call in async thread: {}", asyncThreadName);
            try{
                List<SysData> sysData = sysDataRepository.findAll();
                return CompletableFuture.completedFuture(sysData);
            }catch (DataAccessException ex){
                throw new RuntimeException("Error fetching metadata", ex);
            }
    }

}
