package com.carbon.treasuremap.model;

public class TreasureTile implements Tile {
    private int treasuresAmount;

    private TreasureTile(int amount) {
        if (amount < 1) throw new IllegalArgumentException("""
                Invalid treasure count for tile: %d
                Must count at least 1 treasure.""".formatted(amount));
        treasuresAmount = amount;
    }

    static TreasureTile newTile(int amount) {
        return new TreasureTile(amount);
    }

    @Override
    public boolean isMountain() {
        return false;
    }

    @Override
    public int getTreasuresCount() {
        return treasuresAmount;
    }

    public void dropTreasure() {
        if (!hasTreasures()) throw new IllegalStateException("Attempt to pick a treasure from an empty tile.");
        treasuresAmount--;
    }
}
