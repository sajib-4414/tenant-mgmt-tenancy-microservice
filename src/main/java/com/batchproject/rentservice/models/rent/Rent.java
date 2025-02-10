package com.batchproject.rentservice.models.rent;

import com.batchproject.rentservice.models.BaseEntity;
import com.batchproject.rentservice.models.tenant.TenantProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
//this rent will be created by system, at the end of month, this is another job.
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rent")
public class Rent extends BaseEntity {


    @Column(name = "amount")
    private Double amount;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "paid_date")
    private LocalDate paidDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RentStatus status;

    @JoinColumn(name = "tenant_profile_id")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private TenantProfile tenantProfile;

    @Column(name = "suite_id")
    private Long suiteId;
}
