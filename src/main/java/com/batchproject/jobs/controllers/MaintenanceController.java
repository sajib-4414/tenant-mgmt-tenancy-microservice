package com.batchproject.jobs.controllers;

import com.batchproject.jobs.models.maintenance.Maintenance;
import com.batchproject.jobs.models.maintenance.MaintenanceDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
@RequestMapping("/api/maintenances")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Maintenance>>> getAllMaintenances() {
        return maintenanceService.getAllMaintenances()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Maintenance>> getMaintenanceDetail(@PathVariable Long id) {
        return maintenanceService.getMaintenanceDetail(id)
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<Maintenance>> updateMaintenance(@PathVariable Long id, @RequestBody MaintenanceDTO updatedDto) {
        return maintenanceService.updateMaintenance(id, updatedDto)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteMaintenance(@PathVariable Long id) {
        return maintenanceService.deleteMaintenance(id)
                .thenApply(ResponseEntity::ok);
    }


}
