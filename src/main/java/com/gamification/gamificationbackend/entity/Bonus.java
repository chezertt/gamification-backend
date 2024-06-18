package com.gamification.gamificationbackend.entity;

import com.gamification.gamificationbackend.util.DateTimeUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "bonuses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @ManyToOne
    private BonusType type;

    private String name;

    private OffsetDateTime createdAt;

    @ManyToMany
    @JoinTable(name = "players_obtained_bonuses",
            joinColumns = @JoinColumn(name = "obtained_bonus_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<Player> playersWhoObtainBonus;

    @ManyToMany
    @JoinTable(name = "events_bonuses",
            joinColumns = @JoinColumn(name = "bonus_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;

    @OneToOne(mappedBy = "bonus", fetch = FetchType.LAZY)
    private ShopItem shopItem;

    @PrePersist
    private void prePersist() {
        createdAt = DateTimeUtil.nowInUTC();
    }
}
