package com.batchproject.rentservice.controllers;

import com.batchproject.rentservice.models.tenant.Tenancy;
import com.batchproject.rentservice.models.tenant.TenancyDTO;
import com.batchproject.rentservice.services.TenancyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/tenancy")
public class TenancyController {

    private final TenancyService tenancyService;

    public TenancyController(TenancyService tenancyService) {
        this.tenancyService = tenancyService;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Tenancy>>> getAllTenancies() {
        return tenancyService.getAllTenancies()
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Tenancy>> createTenancy(@Valid @RequestBody TenancyDTO tenancyDTO) {
        return tenancyService.createTenancy(tenancyDTO)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Tenancy>> getTenancyById(@PathVariable Long id) {
        return tenancyService.getTenancyById(id)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/history-by-suite/{suiteId}")
    public CompletableFuture<ResponseEntity<List<Tenancy>>> getTenancyHistoryBySuite(@PathVariable Long suiteId) {
        return tenancyService.getTenancyHistoryBySuite(suiteId)
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<Tenancy>> updateTenancy(@PathVariable Long id, @RequestBody TenancyDTO tenancyDTO) {
        return tenancyService.updateTenancy(id, tenancyDTO)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteTenancy(@PathVariable Long id) {
        return tenancyService.deleteTenancy(id)
                .thenApply(ResponseEntity::ok);
    }
}
