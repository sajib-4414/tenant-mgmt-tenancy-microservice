package com.batchproject.rentservice.services;


import com.batchproject.rentservice.models.tenant.TenantHistory;
import com.batchproject.rentservice.models.tenant.TenantHistoryDTO;
import com.batchproject.rentservice.models.tenant.TenantHistoryRepository;
import com.batchproject.rentservice.models.tenant.TenantProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class TenantHistoryService {

    private final TenantHistoryRepository tenantHistoryRepository;
    private final TenantProfileRepository tenantProfileRepository;


    @Async
    public CompletableFuture<List<TenantHistory>> getAllTenantHistories() {
        return CompletableFuture.completedFuture(
                tenantHistoryRepository.findAll()
                        .stream()
                        .toList()
        );
    }

    @Async
    public CompletableFuture<TenantHistory> createTenantHistory(TenantHistoryDTO payload) {
        //due to moving to microservice we are not right now able to validate a house building exists or not
        TenantHistory tenantHistory = TenantHistory.builder()
                .tenantProfile(tenantProfileRepository.findById(payload.getTenantProfileId()).orElseThrow(()->new RuntimeException("tenant profile is invalid")))
                .isCurrentTenant(payload.getIsCurrentTenant())
                .endDate(payload.getEndDate())
                .startDate(payload.getStartDate())
                .housingBuildingStayedId(payload.getHousingBuildingId()) //housingBuildingRepository.findById(payload.getHousingBuildingId()).orElseThrow(()->new RuntimeException("housing building is invalid")))
                .build();
        return CompletableFuture.completedFuture(tenantHistoryRepository.save(tenantHistory));
    }

    @Async
    public CompletableFuture<TenantHistory> updateTenantHistory(Long id, TenantHistoryDTO payload) {
        TenantHistory existingTenantHistory = tenantHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TenantHistory not found with id: " + id));
//due to moving to microservice we are not abler to validate the housing building id
        if(payload.getTenantProfileId() !=null)
            existingTenantHistory.setTenantProfile(tenantProfileRepository.findById(payload.getTenantProfileId()).orElseThrow(()->new RuntimeException("tenant profile is invalid")));
        if(payload.getStartDate() !=null)
            existingTenantHistory.setStartDate(payload.getStartDate());
        if(payload.getEndDate() !=null)
            existingTenantHistory.setEndDate(payload.getEndDate());
        if(payload.getIsCurrentTenant() !=null)
            existingTenantHistory.setIsCurrentTenant(payload.getIsCurrentTenant());
        if(payload.getHousingBuildingId() !=null)
            existingTenantHistory.setHousingBuildingStayedId(payload.getHousingBuildingId());//housingBuildingRepository.findById(payload.getHousingBuildingId()).orElseThrow(()->new RuntimeException("housing building is invalid")));

        return CompletableFuture.completedFuture(tenantHistoryRepository.save(existingTenantHistory));
    }

    @Async
    public CompletableFuture<TenantHistory> deleteTenantHistory(Long id) {

        tenantHistoryRepository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }


}
