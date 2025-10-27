package com.integralearn.api.domain;

import java.io.Serializable;

public class UserAchievementKey implements Serializable {

    private Long userId;
    private Long achievementId;

    public UserAchievementKey() {
    }

    public UserAchievementKey(Long userId, Long achievementId) {
        this.userId = userId;
        this.achievementId = achievementId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAchievementKey)) {
            return false;
        }
        UserAchievementKey that = (UserAchievementKey) o;
        return (userId != null ? userId.equals(that.userId) : that.userId == null)
                && (achievementId != null ? achievementId.equals(that.achievementId) : that.achievementId == null);
    }

    @Override
    public int hashCode() {
        int result = (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (achievementId != null ? achievementId.hashCode() : 0);
        return result;
    }
}
