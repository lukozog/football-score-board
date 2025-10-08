package com.lukozog.game.domain.dto;

public record StartGameCommand(
        TeamDto homeTeam,
        TeamDto awayTeam
) {
    public record TeamDto(Long id) {
    }
}
