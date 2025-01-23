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
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "id", unique = true, nullable = false))
})
@Table(name = "rent_price")
@AllArgsConstructor
@NoArgsConstructor
public class RentPrice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rent_price_id_seq")
    @SequenceGenerator(name = "rent_price_id_seq", sequenceName = "rent_price_id_seq", allocationSize = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;


    @Column(name = "effective_start_date")
    private LocalDate effectiveStartDate;

    @Column(name = "effective_end_date")
    private LocalDate effectiveEndDate;

    @Column(name = "rent_amount")
    private Double rentAmt;


    @Column(name = "suite_id")
    private Long suiteId;
}
