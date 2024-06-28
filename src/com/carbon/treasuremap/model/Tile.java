package com.carbon.treasuremap.model;

public interface Tile {
    boolean isMountain();

    default boolean hasTreasures() {
        return getTreasuresCount() > 0;
    }

    int getTreasuresCount();

    void dropTreasure();
}
