package com.carbon.treasuremap.model;

import java.util.Objects;

public enum Orientation {
    NORTH(new Coordinates(0, -1)),
    EAST(new Coordinates(1, 0)),
    SOUTH(new Coordinates(0, 1)),
    WEST(new Coordinates(-1, 0));

    private final Coordinates vector;

    Orientation(Coordinates coordinates) {
        vector = Objects.requireNonNull(coordinates);
    }

    Coordinates getDirectionVector() {
        return vector;
    }

    public Orientation rotatedLeft() {
        switch (this) {
            case NORTH -> {
                return WEST;
            }
            case EAST -> {
                return NORTH;
            }
            case SOUTH -> {
                return EAST;
            }
            case WEST -> {
                return SOUTH;
            }
            default -> throw new IllegalStateException("Unknown orientation: '" + this + "'");
        }
    }

    public Orientation rotatedRight() {
        switch (this) {
            case NORTH -> {
                return EAST;
            }
            case EAST -> {
                return SOUTH;
            }
            case SOUTH -> {
                return WEST;
            }
            case WEST -> {
                return NORTH;
            }
            default -> throw new IllegalStateException("Unknown orientation: '" + this + "'");
        }
    }
}
