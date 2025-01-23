package com.batchproject.jobs.models.rent;

import com.batchproject.jobs.models.BaseEntity;
import com.batchproject.jobs.models.tenant.TenantProfile;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
//this rent will be created by system, at the end of month, this is another job.
@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "id", unique = true, nullable = false))
})
@Table(name = "rent")
public class Rent extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rent_id_seq")
    @SequenceGenerator(name = "rent_id_seq", sequenceName = "rent_id_seq", allocationSize = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;


    @Column(name = "amount")
    private int amount;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "paid_date")
    private LocalDate paidDate;
    @Column(name = "status")
    private String status;

    @JoinColumn(name = "tenant_profile_id")
    @ManyToOne
    private TenantProfile tenantProfile;

    @Column(name = "suite_id")
    private Long suiteId;
}
