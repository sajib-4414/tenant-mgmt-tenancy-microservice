package com.batchproject.jobs.controllers;

import com.batchproject.jobs.models.housing.HousingBuilding;
import com.batchproject.jobs.models.housing.HousingDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/housing-buildings")
@AllArgsConstructor
public class HousingController {
    private final HousingService housingService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<HousingBuilding>>> getAllBuildings() {

        CompletableFuture<ResponseEntity<List<HousingBuilding>>> future = housingService.getAllBuildings()
                .thenApply(ResponseEntity::ok);

        return future;
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<HousingBuilding>> getBuildingById(@PathVariable Long id) {

        CompletableFuture<ResponseEntity<HousingBuilding>> future = housingService.getBuildingById(id)
                .thenApply(ResponseEntity::ok);

        return future;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<HousingBuilding>> createBuilding(@RequestBody HousingDTO payload) {
        CompletableFuture<ResponseEntity<HousingBuilding>> future = housingService.createBuilding(payload)
                .thenApply(ResponseEntity::ok);

        return future;
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<HousingBuilding>> updateBuilding(
            @PathVariable Long id, @Valid  @RequestBody HousingDTO payload) {

        return housingService.updateBuilding(id, payload)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteBuilding(@PathVariable Long id) {
        return housingService.deleteBuilding(id)
                .thenApply(_ -> ResponseEntity.noContent().<Void>build());

    }





}
