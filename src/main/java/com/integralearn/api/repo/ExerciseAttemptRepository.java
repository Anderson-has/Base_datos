package com.integralearn.api.repo;

import com.integralearn.api.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExerciseAttemptRepository extends JpaRepository<ExerciseAttempt, Long> {

    List<ExerciseAttempt> findByUserIdAndExerciseId(Long userId, Long exerciseId);

    List<ExerciseAttempt> findByUserId(Long userId);
}
