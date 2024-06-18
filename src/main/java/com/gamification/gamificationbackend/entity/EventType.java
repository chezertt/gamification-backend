package com.gamification.gamificationbackend.entity;

import com.gamification.gamificationbackend.util.DateTimeUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "event_types")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    private String name;

    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "type")
    private List<Event> events;

    @PrePersist
    private void prePersist() {
        createdAt = DateTimeUtil.nowInUTC();
    }
}
