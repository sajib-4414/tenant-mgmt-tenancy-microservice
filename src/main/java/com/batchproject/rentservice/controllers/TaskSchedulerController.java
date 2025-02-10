package com.batchproject.rentservice.controllers;

import com.batchproject.rentservice.models.rent.Rent;
import com.batchproject.rentservice.models.rent.RentCollectJobDTO;
import com.batchproject.rentservice.services.SchedulingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/schedule/")
@AllArgsConstructor
public class TaskSchedulerController {

    private final SchedulingService schedulingService;

    @PostMapping("process-rent")
    //the job is idempotent, meaning we can set the job multiple times, but it won't rewrite if data exists
    //job is supposed to read csv file collected from a directory
    // we will add batch job, then add THousands of data and see what happens, we will use the H2 database for this.
    public ResponseEntity<String> scheduleRentCollectionJob(@Valid @RequestBody RentCollectJobDTO payload) {
        Integer month = payload.getDate().getMonthValue();
        Integer year = payload.getDate().getYear();
        schedulingService.processRent(month, year, payload.getRunAt());
        return ResponseEntity.ok("Job scheduled");
    }

    //another api will come it will query some specific table data
    // running in batch and generate pdf report
    //will do the pdf generation incrementally


    //interview answer e bolte pari, kisu server e alltime job likha ase, egula change korte hole amra code dev, test, then prod e dei
    //db restore kre dba, then test kore

    //ar batch jb er kisu server ase, amra on demand code restart korte pari, like development, and can run job.
}
