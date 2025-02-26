package com.batchproject.rentservice.services;


import com.batchproject.rentservice.models.rent.Rent;
import com.batchproject.rentservice.models.rent.RentDTO;
import com.batchproject.rentservice.models.rent.RentRepository;
import com.batchproject.rentservice.models.tenant.TenantProfile;
import com.batchproject.rentservice.models.tenant.TenantProfileRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class RentService {
    private final RentRepository rentRepository;
    private final TenantProfileRepository tenantProfileRepository;


    @Async
    public CompletableFuture<List<Rent>> getAllRents() {
        List<Rent> rents = rentRepository.findAll();

        return CompletableFuture.completedFuture(rents);
    }

    @Async
    public CompletableFuture<Rent> createRent(RentDTO rentDTO) {
        Optional<TenantProfile> tenantProfileOpt = tenantProfileRepository.findById(rentDTO.getTenantProfileId());


        if (!tenantProfileOpt.isPresent() || rentDTO.getSuiteId()==null) {
            throw new RuntimeException("TenantProfile or Suite not found");
        }

        Rent rent = Rent.builder()
                .amount(rentDTO.getAmount())
                .dueDate(rentDTO.getDueDate())
                .paidDate(rentDTO.getPaidDate())
                .status(rentDTO.getStatus())
                .tenantProfile(tenantProfileOpt.orElseThrow(()->new RuntimeException("Tenant profile not found")))
                .suiteId(rentDTO.getSuiteId())
                .build();

        Rent savedRent = rentRepository.save(rent);

        return CompletableFuture.completedFuture(rent);
    }

    @Async
    @Transactional
    public CompletableFuture<Rent> updateRent(Long rentId, RentDTO rentDTO) {
        Optional<Rent> rentOpt = rentRepository.findById(rentId);
        if (!rentOpt.isPresent()) {
            throw new RuntimeException("Rent not found");
        }

        Rent rent = rentOpt.get();
        Optional<TenantProfile> tenantProfileOpt = tenantProfileRepository.findById(rentDTO.getTenantProfileId());
//        Optional<Suite> suiteOpt = suiteRepository.findById(rentDTO.getSuiteId());
        //in this microservie we dont have access to suite

        //we could call the main microservice syncrhonously in future to validate
        if (!tenantProfileOpt.isPresent() || rentDTO.getSuiteId()==null) {
            throw new RuntimeException("TenantProfile or Suite not found");
        }

        rent.setAmount(rentDTO.getAmount());
        rent.setDueDate(rentDTO.getDueDate());
        rent.setPaidDate(rentDTO.getPaidDate());
        rent.setStatus(rentDTO.getStatus());
        rent.setTenantProfile(tenantProfileOpt.get());
        rent.setSuiteId(rentDTO.getSuiteId());

        Rent updatedRent = rentRepository.save(rent);



        return CompletableFuture.completedFuture(updatedRent);
    }

    @Async
    @Transactional
    public CompletableFuture<Void> deleteRent(Long rentId) {
        Optional<Rent> rentOpt = rentRepository.findById(rentId);
        if (!rentOpt.isPresent()) {
            throw new RuntimeException("Rent not found");
        }

        rentRepository.delete(rentOpt.get());
        return CompletableFuture.completedFuture(null);
    }

}
