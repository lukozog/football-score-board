package com.lukozog.game.domain;

import com.lukozog.game.infrastracture.IdGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
class Team {
    private final Long id;
    private final String name;

    Team(String name) {
        this.id = IdGenerator.generateId();
        this.name = name;
    }
}
