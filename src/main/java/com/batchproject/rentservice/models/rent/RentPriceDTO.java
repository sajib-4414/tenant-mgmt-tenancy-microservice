package com.batchproject.rentservice.models.rent;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RentPriceDTO {

    private LocalDate effectiveStartDate;
    private LocalDate effectiveEndDate;
    private Double rentAmt;
    private Long suiteId;
}
