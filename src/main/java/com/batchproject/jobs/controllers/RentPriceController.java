package com.batchproject.jobs.controllers;

import com.batchproject.jobs.models.rent.RentPrice;
import com.batchproject.jobs.models.rent.RentPriceDTO;
import com.batchproject.jobs.services.RentPriceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rent-price")
public class RentPriceController {

    private final RentPriceService rentPriceService;


    @PostMapping
    public CompletableFuture<ResponseEntity<RentPrice>> setNewRentPrice(@RequestBody RentPriceDTO rentPriceDTO) {
        return rentPriceService.setNewRent(rentPriceDTO)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/rent-history-by-suite/{suiteId}")
    public CompletableFuture<ResponseEntity<List<RentPrice>>> getAllRentPricesBySuite(@PathVariable Long suiteId) {
        return rentPriceService.getAllRentPricesBySuite(suiteId)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/get-latest-rent-price/{suiteId}")
    public CompletableFuture<ResponseEntity<RentPrice>> getLatestRentPriceBySuite(@PathVariable Long suiteId) {
        return rentPriceService.fetchLatestRentOfSuite(suiteId)
                .thenApply(ResponseEntity::ok);
    }


    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<RentPrice>> updateRentPrice(@PathVariable Long id, @RequestBody RentPriceDTO rentPriceDTO) {
        return rentPriceService.updateOldRent(id, rentPriceDTO)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteRentPrice(@PathVariable Long id) {
        return rentPriceService.deleteRentPrice(id)
                .thenApply(ResponseEntity::ok);
    }
}