package com.lukozog.game.domain.dto;

import com.lukozog.game.domain.exception.ValidationException;

public record UpdateScoreCommand(
        Long gameId,
        Integer homeScore,
        Integer awayScore
) {
    public UpdateScoreCommand {
        if (gameId == null) {
            throw new ValidationException("Game id must be set");
        }
        if (homeScore == null || awayScore == null) {
            throw new ValidationException("Score must be set");
        }
        if (homeScore < 0 || awayScore < 0) {
            throw new ValidationException("Score must be positive");
        }
    }
}
