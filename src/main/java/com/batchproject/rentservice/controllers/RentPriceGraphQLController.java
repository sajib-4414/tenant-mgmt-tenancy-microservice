package com.batchproject.rentservice.controllers;

import com.batchproject.rentservice.models.rent.RentPrice;
import com.batchproject.rentservice.models.rent.RentPriceDTO;
import com.batchproject.rentservice.services.RentPriceService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
public class RentPriceGraphQLController {

    private final RentPriceService rentPriceService;

    public RentPriceGraphQLController(RentPriceService rentPriceService) {
        this.rentPriceService = rentPriceService;
    }

    @MutationMapping
    public RentPrice createRentPrice(@Argument RentPriceDTO payload) {
        CompletableFuture<RentPrice> future =  rentPriceService.setNewRent(payload);

        return future.join();
    }

    @QueryMapping
    public List<RentPrice> rentHistoryBySuite(@Argument Long suiteId) {
        CompletableFuture<List<RentPrice>> future =  rentPriceService.getAllRentPricesBySuite(suiteId);
        return future.join();

    }
}
