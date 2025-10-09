package com.lukozog.game.domain.dto;

public record ScoreSummaryDto(
        Long gameId,
        TeamDto homeTeam,
        TeamDto awayTeam
) {

    public record TeamDto(
            String name,
            Integer score
    ) {
    }
}
