package com.gamification.gamificationbackend.entity;

import com.gamification.gamificationbackend.util.DateTimeUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "statistics")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Company company;

    private Integer completedEvents;

    private Integer obtainedBonuses;

    private LocalDate createdAt;

    @PrePersist
    private void prePersist() {
        createdAt = DateTimeUtil.currentDateInUTC();
    }
}
