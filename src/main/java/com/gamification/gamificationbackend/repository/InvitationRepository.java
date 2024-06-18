package com.gamification.gamificationbackend.repository;

import com.gamification.gamificationbackend.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    List<Invitation> findAllByCompanyIdOrderBySentAtDesc(Long companyId);

    boolean existsByPlayerEmailIgnoreCaseAndCompanyId(String playerEmail, Long companyId);
}
