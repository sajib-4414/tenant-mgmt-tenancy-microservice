package com.batchproject.jobs.models.rent;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RentDTO {
    private int amount;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private String status;
    private Long tenantProfileId; // Using tenant profile ID as reference
    private Long suiteId; // Using suite ID as reference
}
