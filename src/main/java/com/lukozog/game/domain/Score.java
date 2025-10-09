package com.lukozog.game.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
class Score {
    private int homeScore;
    private int awayScore;
    private int totalScore;

    Score() {
        this.homeScore = 0;
        this.awayScore = 0;
        this.totalScore = 0;
    }

    void update(Integer homeScore, Integer awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.totalScore = homeScore + awayScore;
    }
}
