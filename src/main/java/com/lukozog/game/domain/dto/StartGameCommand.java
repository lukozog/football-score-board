package com.lukozog.game.domain.dto;

import com.lukozog.game.domain.exception.ValidationException;

import java.util.List;

public record StartGameCommand(
        TeamDto homeTeam,
        TeamDto awayTeam
) {
    public StartGameCommand {
        if (homeTeam == null || awayTeam == null) {
            throw new ValidationException("Home and away team must be set");
        }
        if (homeTeam.id().equals(awayTeam.id())) {
            throw new ValidationException("Home and away team cannot be the same");
        }
    }

    public record TeamDto(Long id) {
        public TeamDto {
            if (id == null) {
                throw new ValidationException("Team id must be set");
            }
        }
    }

    public List<Long> getTeamIds() {
        return List.of(homeTeam.id(), awayTeam.id());
    }

}
