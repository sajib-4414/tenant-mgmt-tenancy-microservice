package com.batchproject.jobs.services;

import com.batchproject.jobs.configs.exceptions.customexceptions.BadDataException;
import com.batchproject.jobs.models.rent.RentPrice;
import com.batchproject.jobs.models.rent.RentPriceRepository;
import com.batchproject.jobs.models.tenant.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class TenancyService {

    private final TenancyRepository tenancyRepository;
    private final TenantProfileRepository tenantProfileRepository;
    private final RentPriceRepository rentPriceRepository;



    @Async
    public CompletableFuture<List<Tenancy>> getAllTenancies() {
        return CompletableFuture.completedFuture(
                tenancyRepository.findAll()

        );
    }

    @Async
    public CompletableFuture<Tenancy> createTenancy(TenancyDTO tenancyDTO) {
        //due to moving to microservice, we are right now not able to check if the suiteid is valid.
//        Suite suite = suiteRepository.findById(tenancyDTO.getSuiteId())
//                .orElseThrow(() -> new EntityNotFoundException("Suite not found with id: " + tenancyDTO.getSuiteId()));
        TenantProfile tenantProfile = tenantProfileRepository.findById(tenancyDTO.getTenantProfileId())
                .orElseThrow(() -> new EntityNotFoundException("TenantProfile not found with id: " + tenancyDTO.getTenantProfileId()));

        LocalDate today = LocalDate.now();
        if(tenancyDTO.getStartDate().isBefore(today))
            throw new BadDataException("invalid start date, should be at least today");
        Tenancy tenancy = new Tenancy();
        tenancy.setSuiteId(tenancyDTO.getSuiteId());
        tenancy.setTenantProfile(tenantProfile);
        tenancy.setStartDate(tenancyDTO.getStartDate());
        tenancy.setEndDate(tenancyDTO.getEndDate());
        //get the latest rent of the suite
        RentPrice latestRent = rentPriceRepository.getLatestRentPrice(tenancyDTO.getSuiteId());
        if(latestRent == null)
            throw new BadDataException("Suite requested does not have any rent associated");
        tenancy.setRentPaid(latestRent.getRentAmt());

        return CompletableFuture.completedFuture(tenancyRepository.save(tenancy));
    }

    @Async
    public CompletableFuture<Tenancy> getTenancyById(Long id) {
        return CompletableFuture.completedFuture(
                tenancyRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Tenancy not found with id: " + id))
        );
    }

    @Async
    public CompletableFuture<List<Tenancy>> getTenancyHistoryBySuite(Long suiteId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "startDate");
        var tenancyList = tenancyRepository.findAllBysuiteId(suiteId, sort);
        return CompletableFuture.completedFuture(
                tenancyList);

    }

    @Async
    public CompletableFuture<Tenancy> updateTenancy(Long id, TenancyDTO tenancyDTO) {
        Tenancy existingTenancy = tenancyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tenancy not found with id: " + id));

        //due to moving to microservice, we are right now not able to check if the suiteid is valid.
//        Suite suite = suiteRepository.findById(tenancyDTO.getSuiteId())
//                .orElseThrow(() -> new EntityNotFoundException("Suite not found with id: " + tenancyDTO.getSuiteId()));
        TenantProfile tenantProfile = tenantProfileRepository.findById(tenancyDTO.getTenantProfileId())
                .orElseThrow(() -> new EntityNotFoundException("TenantProfile not found with id: " + tenancyDTO.getTenantProfileId()));

        existingTenancy.setSuiteId(tenancyDTO.getSuiteId());
        existingTenancy.setTenantProfile(tenantProfile);
        existingTenancy.setStartDate(tenancyDTO.getStartDate());
        existingTenancy.setEndDate(tenancyDTO.getEndDate());
//        existingTenancy.setRentPaid(tenancyDTO); rent is not updateable from tnenacy,
        //in order to update rent, terminate the current tenancy, update the rent, and recreate another tenancy
        existingTenancy.setNotes(tenancyDTO.getNotes());
        return CompletableFuture.completedFuture(tenancyRepository.save(existingTenancy));
    }

    @Async
    public CompletableFuture<Void> deleteTenancy(Long id) {
        tenancyRepository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }
}
