package com.batchproject.rentservice.models.taskmodels;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobExecutionRepository extends JpaRepository<JobExecutionLog, Long> {

}
