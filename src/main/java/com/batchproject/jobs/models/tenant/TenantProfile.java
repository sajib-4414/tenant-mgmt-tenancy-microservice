package com.batchproject.jobs.models.tenant;

import com.batchproject.jobs.models.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
        name = "tenant_profile",
        indexes = {
                @Index(name = "idx_keycloak_user_id_tenant_profile", columnList = "keycloak_user_id")
        }
)

public class TenantProfile extends BaseEntity {


    @Column(name = "keycloak_user_id", unique = true, nullable = false)
    private String keycloakUserId; // This will link to the Keycloak user's ID

    @Column(name = "email")
    private String email;
    @Column(name = "phone_no")
    private String phoneNo;
    @Column(name = "notes")
    private String notes;//notes like if he has been ever evicted like this.
}
