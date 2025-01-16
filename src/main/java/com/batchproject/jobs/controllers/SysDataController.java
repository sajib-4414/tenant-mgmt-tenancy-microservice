package com.batchproject.jobs.controllers;

import com.batchproject.jobs.models.SysData;
import com.batchproject.jobs.services.SysDataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/metadata")
@AllArgsConstructor
@Slf4j
public class SysDataController {

    private final SysDataService sysDataService;

//    @GetMapping("")
//    public DeferredResult<ResponseEntity<List<SysData>>> getAllMetadata(){
//        DeferredResult<ResponseEntity<List<SysData>>> deferredResult = new DeferredResult<>(5000L);
//        CompletableFuture<List<SysData>> future = sysDataService.getAllSysData();
//        future.thenAccept(result-> {
//                    String completionThread = Thread.currentThread().getName();
//                    log.info("Setting DeferredResult in thread: {}", completionThread);
//                    deferredResult.setResult(ResponseEntity.ok(result));
//                });
//        deferredResult.onTimeout(() -> {
//            log.warn("DeferredResult timed out in thread: {}",
//                    Thread.currentThread().getName());
//            deferredResult.setErrorResult(
//                    ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build());
//        });
//        log.info("servlet thread freed");
//        //will handle this later
////                .exceptionally(ex->{
////                    String errorMessage = "Error: " + ex.getMessage();
////                    deferredResult.setResult(ResponseEntity.badRequest().body(errorMessage));
////                })
//        return deferredResult;
//    }

    @GetMapping("/future")
    public CompletableFuture<ResponseEntity<List<SysData>>> getAllMetadataFuture() {

        //getAllSysData is an async method
        CompletableFuture<ResponseEntity<List<SysData>>> future = sysDataService.getAllSysData()
                .thenApply(ResponseEntity::ok);
        return future;
    }
}
