package com.gamification.gamificationbackend.entity;

import com.gamification.gamificationbackend.util.DateTimeUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "shop_items")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @OneToOne
    private Bonus bonus;

    private Integer priceInCoins;

    private Boolean isAvailable;

    private OffsetDateTime createdAt;

    @PrePersist
    private void prePersist() {
        isAvailable = true;
        createdAt = DateTimeUtil.nowInUTC();
    }
}
