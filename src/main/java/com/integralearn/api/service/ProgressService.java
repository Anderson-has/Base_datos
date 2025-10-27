package com.integralearn.api.service;

import com.integralearn.api.dto.LeaderboardRowDto;
import com.integralearn.api.dto.ProgressRowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class ProgressService {

    @Autowired
    private JdbcTemplate jdbc;

    public List<ProgressRowDto> progressForUser(String username) {
        String sql = """
          SELECT user_id, username, scenario_id, scenario_code, best_score, total_score,
                 attempts_count, total_time_seconds, last_activity_at
          FROM v_user_progress
          WHERE username = ?
          ORDER BY scenario_code
        """;
        return jdbc.query(sql, (rs, i) -> mapProgress(rs), username);
    }

    public List<LeaderboardRowDto> leaderboard(String scenarioCode) {
        String sql = """
          SELECT scenario_id, scenario_code, user_id, username, best_score
          FROM v_leaderboard_scenario
          WHERE scenario_code = ?
          ORDER BY best_score DESC, username ASC
        """;
        return jdbc.query(sql, (rs, i) -> mapLeaderboard(rs), scenarioCode);
    }

    private static ProgressRowDto mapProgress(ResultSet rs) throws SQLException {
        return new ProgressRowDto(
                rs.getLong("user_id"),
                rs.getString("username"),
                rs.getLong("scenario_id"),
                rs.getString("scenario_code"),
                rs.getInt("best_score"),
                rs.getInt("total_score"),
                rs.getInt("attempts_count"),
                rs.getInt("total_time_seconds"),
                rs.getTimestamp("last_activity_at") != null
                ? rs.getTimestamp("last_activity_at").toLocalDateTime() : null
        );
    }

    private static LeaderboardRowDto mapLeaderboard(ResultSet rs) throws SQLException {
        return new LeaderboardRowDto(
                rs.getLong("scenario_id"),
                rs.getString("scenario_code"),
                rs.getLong("user_id"),
                rs.getString("username"),
                rs.getInt("best_score")
        );
    }
}
