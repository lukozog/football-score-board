package com.lukozog.game.domain;

import com.lukozog.game.domain.dto.FinishGameCommand;
import com.lukozog.game.domain.dto.ScoreSummaryDto;
import com.lukozog.game.domain.dto.StartGameCommand;
import com.lukozog.game.domain.dto.UpdateScoreCommand;
import com.lukozog.game.domain.exception.DomainValidationException;
import com.lukozog.game.domain.exception.ResourceNotFoundException;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

class GameFacade {

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;

    GameFacade(GameRepository gameRepository, TeamRepository teamRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
    }

    Long startGame(StartGameCommand command) {
        List<Long> teamIds = command.getTeamIds();
        Map<Long, Team> existingTeams = teamRepository.findByIds(teamIds);
        validateTeams(existingTeams.values(), teamIds);
        var newGame = new Game(existingTeams.get(command.homeTeam().id()), existingTeams.get(command.awayTeam().id()));
        gameRepository.save(newGame);
        return newGame.getId();
    }

    void finishGame(FinishGameCommand command) {
        gameRepository.findById(command.gameId())
                .ifPresentOrElse(
                        Game::finish,
                        () -> {
                            throw new ResourceNotFoundException("Game not found");
                        }
                );
    }

    void updateScore(UpdateScoreCommand command) {
        gameRepository.findById(command.gameId())
                .ifPresentOrElse(
                        game -> game.updateScore(command.homeScore(), command.awayScore()),
                        () -> {
                            throw new ResourceNotFoundException("Game not found");
                        }
                );

    }

    List<ScoreSummaryDto> getGamesSummary() {
        return gameRepository.getGamesSummary().stream()
                .filter(game -> !game.isFinished())
                .sorted(Comparator.comparing(Game::totalScore).thenComparing(Game::getAddedAt).reversed())
                .map(this::createScoreSummaryDto)
                .toList();
    }

    Game findById(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));
    }

    private void validateTeams(Collection<Team> teams, List<Long> expectedTeamIds) {
        if (teams.size() != expectedTeamIds.size()) {
            throw new ResourceNotFoundException("Team doesn't exist");
        }
        if (gameRepository.isAnyTeamInvolvedInOtherGame(expectedTeamIds)) {
            throw new DomainValidationException("Team is already involved in other game");
        }
    }

    private ScoreSummaryDto createScoreSummaryDto(Game game) {
        var homeTeam = game.getHomeTeam();
        var awayTeam = game.getAwayTeam();
        var score = game.getScore();
        return new ScoreSummaryDto(
                game.getId(),
                new ScoreSummaryDto.TeamDto(
                        homeTeam.getName(),
                        score.getHomeScore()
                ),
                new ScoreSummaryDto.TeamDto(
                        awayTeam.getName(),
                        score.getAwayScore()
                )
        );
    }
}
