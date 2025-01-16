package com.batchproject.jobs.models.tenant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantHistoryRepository extends JpaRepository<TenantHistory,Long> {
}
