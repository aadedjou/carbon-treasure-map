package com.carbon.treasuremap.model;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class Itinerary {
    private final Queue<Direction> directions = new ArrayDeque<>();

    public Itinerary(List<Direction> directions) {
        this.directions.addAll(Objects.requireNonNull(directions));
    }

    Direction nextDirection() {
        if (isCompleted()) throw new IllegalStateException("Illegal attempt to resume completed itinerary");
        return directions.remove();
    }

    boolean isCompleted() {
        return directions.isEmpty();
    }
}
