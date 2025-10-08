package com.lukozog.game.domain;

import com.lukozog.game.domain.dto.FinishGameCommand;
import com.lukozog.game.domain.dto.ScoreSummaryDto;
import com.lukozog.game.domain.dto.StartGameCommand;
import com.lukozog.game.domain.dto.UpdateScoreCommand;

import java.util.List;

public class GameFacade {

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    GameFacade(GameRepository gameRepository, TeamRepository teamRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
    }

    public void startGame(StartGameCommand command) {

    }

    public void finishGame(FinishGameCommand command) {

    }

    public void updateScore(UpdateScoreCommand command) {

    }

    public List<ScoreSummaryDto> getGamesSummary() {
        return List.of();
    }
}
