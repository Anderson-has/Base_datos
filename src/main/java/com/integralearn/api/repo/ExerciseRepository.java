package com.integralearn.api.repo;

import com.integralearn.api.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Optional<Exercise> findByCode(String code);

    List<Exercise> findByScenarioId(Long scenarioId);
}
