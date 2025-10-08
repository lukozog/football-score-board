package com.lukozog.game.domain

import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

import static InMemoryTeamRepository.FRANCE
import static InMemoryTeamRepository.GERMANY

class InMemoryGameRepository implements GameRepository {

    static final Game GERMANY_VS_FRANCE = new Game(
            TestIdGenerator.generateId(),
            new Team(GERMANY.getId(), GERMANY.getName()),
            new Team(FRANCE.getId(), FRANCE.getName()),
            new Score(),
            LocalDateTime.of(2025, 8, 10, 20, 25, 0)
    );

    private final Map<Long, Game> gameIdToGame;

    InMemoryGameRepository() {
        this.gameIdToGame = new ConcurrentHashMap<>();
        gameIdToGame.put(GERMANY_VS_FRANCE.getId(), GERMANY_VS_FRANCE);
    }
}
