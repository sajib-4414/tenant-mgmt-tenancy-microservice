package com.batchproject.jobs.controllers;


import com.batchproject.jobs.models.housing.Suite;
import com.batchproject.jobs.models.housing.SuiteDTO;
import com.batchproject.jobs.models.housing.SuiteDetailsDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/suites")
@AllArgsConstructor
public class SuiteController {

    private final SuitService suiteService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Suite>>> getAllSuites() {
        return suiteService.getAllSuites()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<SuiteDetailsDTO>> getSuiteById(@PathVariable Long id) {
        return suiteService.getSuiteDetails(id)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Suite>> createSuite(@RequestBody SuiteDTO suiteDTO) throws CloneNotSupportedException {
        return suiteService.createSuite(suiteDTO)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteSuite(@PathVariable Long id) {
        return suiteService.deleteSuite(id)
                .thenApply(unused -> ResponseEntity.noContent().<Void>build());

    }
}
