package com.batchproject.jobs.models.tenant;

import com.batchproject.jobs.models.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "id", unique = true, nullable = false))
})
@Table(name="tenant_history")
@Data
@Builder
public class TenantHistory extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tenant_history_id_seq")
    @SequenceGenerator(name = "tenant_history_id_seq", sequenceName = "tenant_history_id_seq", allocationSize = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JoinColumn(name = "tenant_profile_id")
    @ManyToOne
    private TenantProfile tenantProfile;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate; //no end date means its current
    @Column(name = "is_current_tenant")
    private Boolean isCurrentTenant; //also yes means currently renting

    @Column(name = "suite_stayed")
    private Long housingBuildingStayedId;
}
