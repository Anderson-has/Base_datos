package com.integralearn.api.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_achievements")
@IdClass(UserAchievementKey.class)
public class UserAchievement {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "achievement_id")
    private Long achievementId;

    @Column(nullable = false)
    private LocalDateTime awardedAt = LocalDateTime.now();

    public UserAchievement() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Long achievementId) {
        this.achievementId = achievementId;
    }

    public LocalDateTime getAwardedAt() {
        return awardedAt;
    }

    public void setAwardedAt(LocalDateTime awardedAt) {
        this.awardedAt = awardedAt;
    }
}
