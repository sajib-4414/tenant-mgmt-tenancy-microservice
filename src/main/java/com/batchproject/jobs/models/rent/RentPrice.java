package com.batchproject.jobs.models.rent;

import com.batchproject.jobs.models.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@Entity

@Table(name = "rent_price")
@AllArgsConstructor
@NoArgsConstructor
public class RentPrice extends BaseEntity {




    @Column(name = "effective_start_date")
    private LocalDate effectiveStartDate;

    @Column(name = "effective_end_date")
    private LocalDate effectiveEndDate;

    @Column(name = "rent_amount")
    private Double rentAmt;


    @Column(name = "suite_id")
    private Long suiteId;
}
