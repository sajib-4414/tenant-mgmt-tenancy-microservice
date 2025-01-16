package com.batchproject.jobs.controllers;

import com.batchproject.jobs.models.rent.Rent;
import com.batchproject.jobs.models.rent.RentDTO;
import com.batchproject.jobs.services.RentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/rents")
@AllArgsConstructor
public class RentController {
    private final RentService rentService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Rent>>> getAllRents() {
        return rentService.getAllRents()
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Rent>> createRent(@RequestBody RentDTO rentDTO) {
        return rentService.createRent(rentDTO)
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<Rent>> updateRent(@PathVariable Long id, @RequestBody RentDTO rentDTO) {
        return rentService.updateRent(id, rentDTO)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteRent(@PathVariable Long id) {
        return rentService.deleteRent(id)
                .thenApply(ResponseEntity::ok);
    }
}
