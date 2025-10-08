package com.lukozog.game.domain.dto;

public record UpdateScoreCommand(
        Long gameId,
        Integer homeScore,
        Integer awayScore
) {
}
