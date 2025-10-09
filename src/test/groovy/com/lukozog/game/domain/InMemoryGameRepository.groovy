package com.lukozog.game.domain


import java.util.concurrent.ConcurrentHashMap

class InMemoryGameRepository implements GameRepository {

    private final Map<Long, Game> gameIdToGame;

    InMemoryGameRepository() {
        this.gameIdToGame = new ConcurrentHashMap<>();
    }

    @Override
    boolean isAnyTeamInvolvedInOtherGame(List<Long> teamIds) {
        return gameIdToGame.values().stream()
                .anyMatch(game ->
                        teamIds.contains(game.getHomeTeam().getId()) || teamIds.contains(game.getAwayTeam().getId())
                );
    }

    @Override
    void save(Game newGame) {
        gameIdToGame.put(newGame.getId(), newGame);
    }

    @Override
    Optional<Game> findById(Long gameId) {
        return Optional.ofNullable(gameIdToGame.get(gameId));
    }

    @Override
    Collection<Game> getGamesSummary() {
        return gameIdToGame.values();
    }
}
