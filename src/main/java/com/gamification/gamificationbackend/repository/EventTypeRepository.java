package com.gamification.gamificationbackend.repository;

import com.gamification.gamificationbackend.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {

    List<EventType> findAllByCompanyIdOrderByCreatedAtDesc(Long companyId);

    boolean existsByNameIgnoreCaseAndCompanyId(String name, Long companyId);
}
