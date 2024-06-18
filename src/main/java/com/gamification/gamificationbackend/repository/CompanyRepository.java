package com.gamification.gamificationbackend.repository;

import com.gamification.gamificationbackend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    boolean existsByName(String name);
}
