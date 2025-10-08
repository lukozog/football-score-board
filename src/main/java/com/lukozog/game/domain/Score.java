package com.lukozog.game.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
class Score {
    private int homeTeam;
    private int awayTeam;
    private int totalScore;

    Score() {
        this.homeTeam = 0;
        this.awayTeam = 0;
        this.totalScore = 0;
    }
}
