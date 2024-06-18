package com.gamification.gamificationbackend.entity;

import com.gamification.gamificationbackend.util.DateTimeUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @ManyToOne
    private EventType type;

    private String name;

    private String description;

    private Integer points;

    private Integer coins;

    private Boolean isAvailable;

    private OffsetDateTime createdAt;

    @ManyToMany
    @JoinTable(name = "events_bonuses",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "bonus_id"))
    private List<Bonus> bonuses;

    @ManyToMany
    @JoinTable(name = "players_completed_events",
            joinColumns = @JoinColumn(name = "completed_event_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<Player> playersWhoCompletedEvent;

    @PrePersist
    private void prePersist() {
        if (points == null) {
            points = 0;
        }
        if (coins == null) {
            coins = 0;
        }
        isAvailable = true;
        createdAt = DateTimeUtil.nowInUTC();
    }
}
