package com.carbon.treasuremap.model;

import java.util.Objects;

public class Coordinates {
    private int x, y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates other)) return false;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    void add(Coordinates coordinates) {
        x += coordinates.x;
        y += coordinates.y;
    }

    Coordinates addedTo(Coordinates coordinates) {
        return new Coordinates(x + coordinates.x, y + coordinates.y);
    }
}
