package com.gamification.gamificationbackend.repository;

import com.gamification.gamificationbackend.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findAllByUserCompanyIdOrderByPointsDesc(Long companyId);
    List<Player> findAllByUserCompanyId(Long companyId);
}
