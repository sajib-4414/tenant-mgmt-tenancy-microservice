package com.batchproject.jobs.controllers;

import com.batchproject.jobs.models.address.Address;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/addresses")
@AllArgsConstructor
public class AddressController {


    private AddressService addressService;

    @PostMapping
    public CompletableFuture<ResponseEntity<Address>> createAddress(@Valid @RequestBody Address address) {
        CompletableFuture<ResponseEntity<Address>> future =
                addressService.createAddress(address)
                        .thenApply(result-> ResponseEntity.ok(result));

        return future;
    }

    @PutMapping("/{addressId}")
    public CompletableFuture<ResponseEntity<Address>> updateAddress(@PathVariable Long addressId, @Valid @RequestBody Address address) {
        CompletableFuture<ResponseEntity<Address>> future =
                addressService.updateAddress(addressId, address)
                        .thenApply(result-> ResponseEntity.ok(result));
        //if there are errors, they are automatically caught here, and passed to global error handler
        return future;
    }


    @DeleteMapping("/{addressId}")
    public CompletableFuture<ResponseEntity<Void>> deleteAddress(@PathVariable Long addressId) {

        CompletableFuture<ResponseEntity<Void>> future =
                addressService.deleteAddress(addressId)
                        .thenApply(unused-> ResponseEntity.status(HttpStatus.NO_CONTENT).<Void>build());

        return future;
    }

}
