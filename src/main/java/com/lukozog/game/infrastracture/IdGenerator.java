package com.lukozog.game.infrastracture;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private IdGenerator() {
    }

    private static AtomicLong id;

    public static Long generateId() {
        if (id == null) {
            id = new AtomicLong(0L);
        }
        return id.incrementAndGet();
    }
}
