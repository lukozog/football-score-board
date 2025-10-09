package com.lukozog.game.domain;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

interface GameRepository {

    boolean isAnyTeamInvolvedInOtherGame(List<Long> teamIds);

    void save(Game newGame);

    Optional<Game> findById(Long gameId);

    Collection<Game> getGamesSummary();
}
