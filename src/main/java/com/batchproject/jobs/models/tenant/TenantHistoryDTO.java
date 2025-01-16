package com.batchproject.jobs.models.tenant;

import com.batchproject.jobs.models.housing.HousingBuilding;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TenantHistoryDTO {


    private Long tenantProfileId;

    private LocalDate startDate;

    private LocalDate endDate; //no end date means its current

    private Boolean isCurrentTenant; //also yes means currently renting

    private Long housingBuildingId;
}
