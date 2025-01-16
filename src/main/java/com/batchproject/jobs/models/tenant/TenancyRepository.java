package com.batchproject.jobs.models.tenant;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenancyRepository extends JpaRepository<Tenancy,Long> {
    List<Tenancy> findAllBySuite_Id(Long suiteId, Sort sort);
}
