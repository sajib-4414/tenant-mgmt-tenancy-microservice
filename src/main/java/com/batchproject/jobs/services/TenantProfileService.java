package com.batchproject.jobs.services;

import com.batchproject.jobs.configs.exceptions.customexceptions.ItemNotFoundException;
import com.batchproject.jobs.models.tenant.TenantProfile;
import com.batchproject.jobs.models.tenant.TenantProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TenantProfileService {

    private final TenantProfileRepository tenantProfileRepository;



    @Async
    public CompletableFuture<TenantProfile> createTenantProfile(TenantProfile payload) {
        TenantProfile savedProfile = tenantProfileRepository.save(payload);
        return CompletableFuture.completedFuture(savedProfile);
    }

    @Async
    public CompletableFuture<TenantProfile> updateTenantProfile(Long id, TenantProfile updatedTenantProfile) {
        TenantProfile existingProfile = tenantProfileRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("TenantProfile not found"));

        existingProfile.setKeycloakUserId(updatedTenantProfile.getKeycloakUserId());
        existingProfile.setEmail(updatedTenantProfile.getEmail());
        existingProfile.setPhoneNo(updatedTenantProfile.getPhoneNo());
        existingProfile.setNotes(updatedTenantProfile.getNotes());

        TenantProfile savedProfile = tenantProfileRepository.save(existingProfile);
        return CompletableFuture.completedFuture(savedProfile);
    }
}

