package com.batchproject.rentservice.models.rent;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RentCollectJobDTO {
    @NotNull
    private LocalDateTime runAt;
    @NotNull
    private LocalDate date; //it is supposed to send 1st day of a month, if not we will still just extract year and month
}
