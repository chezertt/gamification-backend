package com.gamification.gamificationbackend.repository;

import com.gamification.gamificationbackend.entity.BonusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BonusTypeRepository extends JpaRepository<BonusType, Long> {

    List<BonusType> findAllByCompanyIdOrderByCreatedAtDesc(Long companyId);

    boolean existsByNameIgnoreCaseAndCompanyId(String name, Long companyId);
}
