package com.batchproject.rentservice.services;

import com.batchproject.rentservice.configs.exceptions.customexceptions.UnprocessableEntityException;
import com.batchproject.rentservice.models.rent.RentPrice;
import com.batchproject.rentservice.models.rent.RentPriceDTO;
import com.batchproject.rentservice.models.rent.RentPriceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
        RentPrice savedRentPrice = rentPriceRepository.save(rentPrice);

        return CompletableFuture.completedFuture(savedRentPrice);
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

    public CompletableFuture<List<RentPrice>> getLatestRentPricesBulk(List<Long> suiteIds) {
        List<RentPrice> rentPrices = rentPriceRepository.getLatestRentPriceBulk(suiteIds);

        return CompletableFuture.completedFuture(rentPrices);
    }
}
