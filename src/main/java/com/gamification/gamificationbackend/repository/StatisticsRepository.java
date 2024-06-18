package com.gamification.gamificationbackend.repository;

import com.gamification.gamificationbackend.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    List<Statistics> findTop7ByCompanyIdOrderByCreatedAtDesc(Long companyId);

    @Query(value = """
            SELECT COUNT(*) FROM events e
            JOIN players_completed_events pce ON (e.id = pce.completed_event_id)
            WHERE e.company_id = :companyId AND pce.completed_at > :nowMinusOneDay
            """, nativeQuery = true)
    long countCompletedEventsForLastDayByCompanyId(Long companyId, OffsetDateTime nowMinusOneDay);

    @Query(value = """
            SELECT COUNT(*) FROM bonuses b
            JOIN players_obtained_bonuses pob ON (b.id = pob.obtained_bonus_id)
            WHERE b.company_id = :companyId AND pob.obtained_at > :nowMinusOneDay
            """, nativeQuery = true)
    long countObtainedBonusesForLastDayByCompanyId(Long companyId, OffsetDateTime nowMinusOneDay);
}
