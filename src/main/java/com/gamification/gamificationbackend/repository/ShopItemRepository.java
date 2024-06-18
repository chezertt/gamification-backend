package com.gamification.gamificationbackend.repository;

import com.gamification.gamificationbackend.entity.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopItemRepository extends JpaRepository<ShopItem, Long> {

    List<ShopItem> findAllByCompanyIdOrderByCreatedAtDesc(Long companyId);

    boolean existsByBonusId(Long bonusId);
}
