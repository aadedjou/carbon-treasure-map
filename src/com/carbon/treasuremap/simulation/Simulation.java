package com.carbon.treasuremap.simulation;

import com.carbon.treasuremap.model.Adventurer;
import com.carbon.treasuremap.model.TreasureMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Simulation {
    private final TreasureMap treasureMap;
    private final List<Adventurer> adventurers = new ArrayList<>();

    public Simulation(TreasureMap treasureMap, List<Adventurer> adventurerList) {
        this.treasureMap = Objects.requireNonNull(treasureMap);
        checkIfValid(treasureMap, Objects.requireNonNull(adventurerList));
        adventurers.addAll(adventurerList);
    }

    private static void checkIfValid(TreasureMap treasureMap, List<Adventurer> adventurerList) {
        var distinctPositionsCount = adventurerList.stream()
                .map(Adventurer::getPosition)
                .distinct()
                .count();

        if (distinctPositionsCount < adventurerList.size())
            throw new IllegalArgumentException("Some adventurers were initialized with similar positions.");

        adventurerList.stream().filter(a -> !treasureMap.isInBounds(a.getPosition())).findFirst().ifPresent(a -> {
            throw new IllegalArgumentException("""
                    Initial adventurer position exceeds map's bounds: %s %s -> [%d,%d]"""
                    .formatted(a.getName(), a.getPosition(), treasureMap.getWidth(), treasureMap.getHeight()));
        });
    }

    @Override
    public String toString() {
        return treasureMap + "\n" + adventurers.stream().map(Objects::toString).collect(Collectors.joining("\n"));
    }

    public TreasureMap getTreasureMap() {
        return treasureMap;
    }

    public List<Adventurer> getAllAdventurers() {
        return adventurers;
    }

    public void runAllItineraries() {
        if (adventurers.stream().noneMatch(Adventurer::hasNotCompletedItinerary)) return;
        computeNextStep();
        runAllItineraries();
    }

    void computeNextStep() {
        adventurers.stream()
                .filter(Adventurer::hasNotCompletedItinerary)
                .forEach(a -> a.followNextItineraryStep(this));
    }
}
