package com.batchproject.jobs.models.rent;


import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RentPriceRepository extends JpaRepository<RentPrice, Long> {

    @Query("SELECT rp FROM RentPrice rp " +
            "WHERE CURRENT_DATE BETWEEN rp.effectiveStartDate AND rp.effectiveEndDate AND rp.suiteId=:suiteId " +
            "ORDER BY rp.effectiveStartDate DESC")
    @QueryHints(value = @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE, value = "1"))
    RentPrice getLatestRentPrice(@Param("suiteId") Long suiteId);
    List<RentPrice> findAllBysuiteId(Long suiteId);
}
