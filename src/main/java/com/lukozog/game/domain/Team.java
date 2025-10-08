package com.lukozog.game.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
class Team {
    private Long id;
    private String name;

    Team(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
