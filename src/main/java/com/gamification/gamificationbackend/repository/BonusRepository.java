package com.gamification.gamificationbackend.repository;

import com.gamification.gamificationbackend.entity.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BonusRepository extends JpaRepository<Bonus, Long> {

    List<Bonus> findAllByCompanyIdOrderByCreatedAtDesc(Long companyId);

    List<Bonus> findAllByPlayersWhoObtainBonus_IdOrderByCreatedAtDesc(Long playerId);

    boolean existsByNameIgnoreCaseAndCompanyId(String bonusName, Long companyId);
}
