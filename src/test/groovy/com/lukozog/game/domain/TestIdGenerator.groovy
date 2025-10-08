package com.lukozog.game.domain

import lombok.NoArgsConstructor

import java.util.concurrent.atomic.AtomicLong

@NoArgsConstructor
final class TestIdGenerator {

    private static AtomicLong id;

    static Long generateId() {
        if (id == null) {
            id = new AtomicLong(0L);
        }
        return id.incrementAndGet();
    }
}

