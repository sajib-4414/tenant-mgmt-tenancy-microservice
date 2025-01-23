package com.batchproject.jobs.models.tenant;

import com.batchproject.jobs.models.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "id", unique = true, nullable = false))
})
@Table(name = "tenancy")
public class Tenancy extends BaseEntity {


    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tenancy_id_seq")
    @SequenceGenerator(name = "tenancy_id_seq", sequenceName = "tenancy_id_seq", allocationSize = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;


    @Column(name = "suite_id")
    private Long suiteId;

    @JoinColumn(name = "tenant_profile_id")
    @ManyToOne
    private TenantProfile tenantProfile;

    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "rent_paid")
    private Double rentPaid;

    @Column(name = "notes")
    private String notes;
}
