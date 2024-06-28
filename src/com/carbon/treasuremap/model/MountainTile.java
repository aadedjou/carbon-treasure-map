package com.carbon.treasuremap.model;

public class MountainTile implements Tile {
    private MountainTile() {}

    static MountainTile newTile() {
        return new MountainTile();
    }

    @Override
    public boolean isMountain() {
        return true;
    }

    @Override
    public int getTreasuresCount() {
        return 0;
    }

    @Override
    public void dropTreasure() {
    }
}
