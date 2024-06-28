package com.carbon.treasuremap.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TreasureMap {
    private final int width, height;
    private final Tile[][] tiles;
    private final Map<Coordinates, Tile> tilesCache = new HashMap<>();

    public TreasureMap(int width, int height) {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException("""
                    Invalid dimensions: [%d,%d] for the treasure map.
                    Both width and height must be strictly positive.""".formatted(width, height));
        this.width = width;
        this.height = height;
        tiles = new Tile[height][width];
    }

    @Override
    public String toString() {
        return Arrays.stream(tiles).map(row -> Arrays.stream(row).map(t -> {
            if (t == null) return '.';
            else if (t.isMountain()) return 'M';
            else if (t.hasTreasures()) return 'T';
            else return '.';
        }).map(Object::toString).collect(Collectors.joining(" "))).collect(Collectors.joining("\n"));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile getTileAt(int x, int y) {
        if (!isInBounds(x, y))
            throw new ArrayIndexOutOfBoundsException("""
                    Impossible to access coordinates (%d, %d).
                    Given coordinates exceed map's bounds ([%d,%d])""".formatted(x, y, width, height));
        return tiles[y][x];
    }

    public Tile getTileAt(Coordinates coordinates) {
        return getTileAt(coordinates.getX(), coordinates.getY());
    }

    private boolean isInBounds(int x, int y) {
        return 0 <= x && x < width && 0 <= y && y < height;
    }

    public boolean isInBounds(Coordinates coordinates) {
        return isInBounds(coordinates.getX(), coordinates.getY());
    }

    private boolean isFreeSpace(int x, int y) {
        return getTileAt(x, y) == null;
    }

    public boolean isFreeSpace(Coordinates coordinates) {
        return isFreeSpace(coordinates.getX(), coordinates.getY());
    }

    private void recordNewTile(int x, int y, Tile tile) {
        if (!isInBounds(x, y))
            throw new ArrayIndexOutOfBoundsException("""
                    Invalid coordinates given for '%s': (%d, %d).
                    Given coordinates exceed map's bounds ([%d,%d])""".formatted(tile, x, y, width, height));
        if (!isFreeSpace(x, y)) throw new IllegalArgumentException("""
                Can't put '%s' at (%d,%d); this space is already occupied""".formatted(tile, x, y));
        tiles[y][x] = tile;
        tilesCache.put(new Coordinates(x, y), tile);
    }

    public void putMountainAt(int x, int y) {
        recordNewTile(x, y, MountainTile.newTile());
    }

    public void putTreasuresAt(int x, int y, int amount) {
        recordNewTile(x, y, TreasureTile.newTile(amount));
    }

    public List<Coordinates> getAllMountainsCoordinates() {
        return tilesCache.entrySet().stream().filter(e -> e.getValue().isMountain()).map(Map.Entry::getKey).toList();
    }

    public List<Map.Entry<Coordinates, Tile>> getAllTreasures() {
        return tilesCache.entrySet().stream().filter(e -> e.getValue().hasTreasures()).toList();
    }
}
