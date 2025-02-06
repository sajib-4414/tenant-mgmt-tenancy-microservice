package com.batchproject.rentservice.controllers;

import com.batchproject.rentservice.models.tenant.TenantProfile;
import com.batchproject.rentservice.services.TenantProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/tenant-profiles")
@RequiredArgsConstructor
public class TenantProfileController {

    private final TenantProfileService tenantProfileService;



    @PostMapping
    public CompletableFuture<ResponseEntity<TenantProfile>> createTenantProfile(@RequestBody TenantProfile tenantProfile) {
        return tenantProfileService.createTenantProfile(tenantProfile)
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<TenantProfile>> updateTenantProfile(@PathVariable Long id, @RequestBody TenantProfile updatedTenantProfile) {
        return tenantProfileService.updateTenantProfile(id, updatedTenantProfile)
                .thenApply(ResponseEntity::ok);
    }
}

