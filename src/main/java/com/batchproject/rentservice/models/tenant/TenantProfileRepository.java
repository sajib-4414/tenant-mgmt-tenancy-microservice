package com.batchproject.rentservice.models.tenant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantProfileRepository extends JpaRepository<TenantProfile,Long> {
}
