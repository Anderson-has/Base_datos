package com.integralearn.api.repo;

import com.integralearn.api.domain.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    Optional<Achievement> findByCode(String code);
}
