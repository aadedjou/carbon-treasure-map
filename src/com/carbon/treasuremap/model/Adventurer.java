package com.carbon.treasuremap.model;

import com.carbon.treasuremap.simulation.Simulation;

import java.util.Objects;

public class Adventurer {
    private final String name;
    private final Itinerary itinerary;
    private final Coordinates position;
    private Orientation orientation;
    private int pickedTreasures = 0;

    public Adventurer(String name, Orientation initialOrientation, Itinerary itinerary, int initialX, int initialY) {
        this.name = Objects.requireNonNull(name);
        this.itinerary = Objects.requireNonNull(itinerary);
        orientation = initialOrientation;
        position = new Coordinates(initialX, initialY);
    }

    @Override
    public String toString() {
        return "%s %s [%s]".formatted(name, position, pickedTreasures > 0 ? pickedTreasures : "-");
    }

    public String getName() {
        return name;
    }

    public Coordinates getPosition() {
        return position;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getPickedTreasures() {
        return pickedTreasures;
    }

    public boolean hasNotCompletedItinerary() {
        return !itinerary.isCompleted();
    }

    boolean canMoveForward(Simulation simulation) {
        var map = simulation.getTreasureMap();
        var futurePosition = position.addedTo(orientation.getDirectionVector());
        var landingTile = map.getTileAt(futurePosition);
        var noAdventurersOnTile = simulation.getAllAdventurers().stream()
                .noneMatch(a -> a.getPosition().equals(futurePosition));

        return (map.isFreeSpace(futurePosition) || landingTile.hasTreasures()) && noAdventurersOnTile;
    }

    private void turnLeft() {
        orientation = orientation.rotatedLeft();
    }

    private void turnRight() {
        orientation = orientation.rotatedRight();
    }

    private void moveForward(TreasureMap map) {
        position.add(orientation.getDirectionVector());
        landOnTile(map);
    }

    private void landOnTile(TreasureMap map) {
        if (map.isFreeSpace(position)) return;
        var landingTile = map.getTileAt(position);
        if (landingTile.hasTreasures()) pickTreasure(landingTile);
    }

    private void pickTreasure(Tile tile) {
        tile.dropTreasure();
        pickedTreasures++;
    }

    public void followNextItineraryStep(Simulation simulation) {
        var nextDirection = itinerary.nextDirection();
        switch (nextDirection) {
            case LEFT -> turnLeft();
            case RIGHT -> turnRight();
            case FORWARD -> {
                if (canMoveForward(simulation)) moveForward(simulation.getTreasureMap());
            }
            default -> throw new IllegalStateException("Unknown direction: " + nextDirection);
        }
    }
}
