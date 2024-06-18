package com.gamification.gamificationbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "players")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Player {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private String firstName;

    private String lastName;

    private String title;

    private Integer level;

    private Integer points;

    private Integer currentLevelPoints;

    private Integer nextLevelPoints;

    private Integer coins;

    @ManyToMany
    @JoinTable(name = "players_completed_events",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "completed_event_id"))
    private List<Event> completedEvents;

    @ManyToMany
    @JoinTable(name = "players_obtained_bonuses",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "obtained_bonus_id"))
    private List<Bonus> obtainedBonuses;

    @PrePersist
    private void prePersist() {
        level = 1;
        points = 0;
        currentLevelPoints = 0;
        nextLevelPoints = 100;
        coins = 0;
    }
}
