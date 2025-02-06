package com.batchproject.rentservice.controllers;

import com.batchproject.rentservice.models.BulkIdPayload;
import com.batchproject.rentservice.models.rent.RentPrice;
import com.batchproject.rentservice.models.rent.RentPriceDTO;
import com.batchproject.rentservice.services.RentPriceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
@Slf4j
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

    @PostMapping("/get-latest-rent-price-bulk")
    public CompletableFuture<ResponseEntity<List<RentPrice>>> getLatestBulkRentPriceBySuiteArray(@RequestBody BulkIdPayload payload) {
        List<Long> suiteIds = payload.getIds();
        log.info("the suite ids being sent are");
        log.info(suiteIds.toString());
        return rentPriceService.getLatestRentPricesBulk(suiteIds)
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