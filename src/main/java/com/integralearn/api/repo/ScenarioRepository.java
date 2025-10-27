package com.integralearn.api.repo;

import com.integralearn.api.domain.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {

    Optional<Scenario> findByCode(String code);

    List<Scenario> findByEnabled(boolean enabled);
}
