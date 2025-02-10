package com.batchproject.rentservice.models.rent;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RentDTO {
    private Double amount;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private RentStatus status;
    private Long tenantProfileId; // Using tenant profile ID as reference
    private Long suiteId; // Using suite ID as reference
}
