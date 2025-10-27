package com.integralearn.api.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;

    @Column(unique = true, nullable = false)
    private String code;
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer maxScore;
    private Integer timeLimitSeconds;

    public Exercise() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public Integer getTimeLimitSeconds() {
        return timeLimitSeconds;
    }

    public void setTimeLimitSeconds(Integer timeLimitSeconds) {
        this.timeLimitSeconds = timeLimitSeconds;
    }
}
