package com.gamification.gamificationbackend.repository;

import com.gamification.gamificationbackend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByCompanyIdOrderByCreatedAtDesc(Long companyId);
    List<Event> findAllByPlayersWhoCompletedEvent_IdOrderByCreatedAtDesc(Long playerId);

    boolean existsByNameIgnoreCaseAndCompanyId(String eventName, Long companyId);
}
