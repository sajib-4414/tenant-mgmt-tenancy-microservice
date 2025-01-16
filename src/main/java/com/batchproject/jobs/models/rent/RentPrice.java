package com.batchproject.jobs.models.rent;

import com.batchproject.jobs.models.BaseEntity;
import com.batchproject.jobs.models.housing.Suite;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JoinColumn(name = "suite_id")
    @ManyToOne
    @JsonIgnore
    private Suite suite;
}
