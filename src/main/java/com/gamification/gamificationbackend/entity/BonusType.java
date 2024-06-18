package com.gamification.gamificationbackend.entity;

import com.gamification.gamificationbackend.util.DateTimeUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "bonus_types")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BonusType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    private String name;

    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "type")
    private List<Bonus> bonuses;

    @PrePersist
    private void prePersist() {
        createdAt = DateTimeUtil.nowInUTC();
    }
}
