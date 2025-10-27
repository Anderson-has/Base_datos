package com.integralearn.api.repo;

import com.integralearn.api.domain.UserAchievement;
import com.integralearn.api.domain.UserAchievementKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, UserAchievementKey> {
}
