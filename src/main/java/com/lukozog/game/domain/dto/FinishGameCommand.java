package com.lukozog.game.domain.dto;

import com.lukozog.game.domain.exception.ValidationException;

public record FinishGameCommand(Long gameId) {
    public FinishGameCommand {
        if (gameId == null) {
            throw new ValidationException("Game id must be set");
        }
    }
}
