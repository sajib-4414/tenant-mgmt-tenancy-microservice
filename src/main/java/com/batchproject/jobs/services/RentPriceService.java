package com.batchproject.jobs.services;

import com.batchproject.jobs.configs.exceptions.customexceptions.BadDataException;
import com.batchproject.jobs.configs.exceptions.customexceptions.UnprocessableEntityException;
import com.batchproject.jobs.models.rent.RentPrice;
import com.batchproject.jobs.models.rent.RentPriceDTO;
import com.batchproject.jobs.models.rent.RentPriceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Service
@AllArgsConstructor
public class RentPriceService {

    private final RentPriceRepository rentPriceRepository;


    @Async
    @Transactional
    public CompletableFuture<RentPrice> setNewRent(RentPriceDTO payload) {
//        Suite suite = suiteRepository.findById(payload.getSuiteId())
//                .orElseThrow(() -> new EntityNotFoundException("Suite not found with id: " + payload.getSuiteId()));
        //we cannot verify anymore, but as this will come from the main project, we can assume its verified suite id.

        RentPrice rentPrice = RentPrice.builder()
                .rentAmt(payload.getRentAmt())
                .effectiveStartDate(payload.getEffectiveStartDate())
                .effectiveEndDate(payload.getEffectiveEndDate())
                .suiteId(payload.getSuiteId())
                .build();
        rentPriceRepository.save(rentPrice);
        return CompletableFuture.completedFuture(rentPrice);
    }

    @Async
    public CompletableFuture<List<RentPrice>> getAllRentPricesBySuite(Long suiteId) {
        return CompletableFuture.completedFuture(
                rentPriceRepository.findAllBysuiteId(suiteId)
        );
    }


    @Async
    public CompletableFuture<RentPrice> updateOldRent(Long id, RentPriceDTO rentPriceDTO) {
        RentPrice existingRentPrice = rentPriceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RentPrice not found with id: " + id));

        existingRentPrice.setRentAmt(rentPriceDTO.getRentAmt());
//        Suite suite = suiteRepository.findById(payload.getSuiteId())
//                .orElseThrow(() -> new EntityNotFoundException("Suite not found with id: " + payload.getSuiteId()));
        //we cannot verify anymore, but as this will come from the main project, we can assume its verified suite id.


        existingRentPrice.setEffectiveStartDate(rentPriceDTO.getEffectiveStartDate());
        existingRentPrice.setEffectiveEndDate(rentPriceDTO.getEffectiveEndDate());
        existingRentPrice.setSuiteId(rentPriceDTO.getSuiteId());

        return CompletableFuture.completedFuture(rentPriceRepository.save(existingRentPrice));
    }

    @Async
    public CompletableFuture<Void> deleteRentPrice(Long id) {
        rentPriceRepository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }

    public CompletableFuture<RentPrice> fetchLatestRentOfSuite(Long suiteId) {
        RentPrice rentPrice = rentPriceRepository.getLatestRentPrice(suiteId);
        System.out.println("rent ricre was "+rentPrice);
        if (rentPrice == null)
            throw new UnprocessableEntityException("No rent price found for the suite");
        return CompletableFuture.completedFuture(rentPrice);

    }
}
