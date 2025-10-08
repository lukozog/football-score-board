package com.lukozog.game.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
class Game {
    private Long id;
    private Team homeTeam;
    private Team awayTeam;
    private Score score;
    private boolean finished;
    private LocalDateTime addedAt;

    Game(Long id, Team homeTeam, Team awayTeam, Score score, LocalDateTime addedAt) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.score = score;
        this.addedAt = addedAt;
        this.finished = false;
    }

}
