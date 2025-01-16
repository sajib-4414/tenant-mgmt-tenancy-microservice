package com.batchproject.jobs.models.tenant;

import com.batchproject.jobs.models.BaseEntity;
import com.batchproject.jobs.models.housing.HousingBuilding;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name="tenant_history")
@Data
@Builder
public class TenantHistory extends BaseEntity {

    @JoinColumn(name = "tenant_profile_id")
    @ManyToOne
    private TenantProfile tenantProfile;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate; //no end date means its current
    @Column(name = "is_current_tenant")
    private Boolean isCurrentTenant; //also yes means currently renting
    @JoinColumn(name = "housing_building_stayed")
    @ManyToOne
    private HousingBuilding buildingStayed;
}
