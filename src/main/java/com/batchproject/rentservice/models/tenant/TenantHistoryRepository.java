package com.batchproject.rentservice.models.tenant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantHistoryRepository extends JpaRepository<TenantHistory,Long> {
}
