package com.gamification.gamificationbackend.entity;

import com.gamification.gamificationbackend.util.DateTimeUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "invitations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Company company;

    private String playerEmail;

    private String playerFirstName;

    private String playerLastName;

    private String playerTitle;

    private OffsetDateTime sentAt;

    @PrePersist
    private void prePersist() {
        sentAt = DateTimeUtil.nowInUTC();
    }
}
