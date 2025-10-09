package com.lukozog.game.domain;

import com.lukozog.game.domain.exception.DomainValidationException;
import com.lukozog.game.infrastracture.IdGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
class Game {
    private final Long id;
    private final Team homeTeam;
    private final Team awayTeam;
    private final Score score;
    private boolean finished;
    private final LocalDateTime addedAt;

    Game(Team homeTeam, Team awayTeam) {
        this.id = IdGenerator.generateId();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.score = new Score();
        this.addedAt = LocalDateTime.now();
        this.finished = false;
    }

    void finish() {
        checkIfFinished();
        this.finished = true;
    }

    void updateScore(Integer homeScore, Integer awayScore) {
        checkIfFinished();
        score.update(homeScore, awayScore);
    }

    Integer totalScore() {
        return score.getTotalScore();
    }

    private void checkIfFinished() {
        if (finished) {
            throw new DomainValidationException("Game is already finished");
        }
    }
}
