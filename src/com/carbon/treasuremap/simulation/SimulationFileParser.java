package com.carbon.treasuremap.simulation;

import com.carbon.treasuremap.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.carbon.treasuremap.simulation.SimulationFileConstants.*;

public class SimulationFileParser {
    private final static int
            MAP_ARG_COUNT = 3,
            MOUNTAIN_ARG_COUNT = 3,
            TREASURE_ARG_COUNT = 4,
            ADVENTURER_ARG_COUNT = 6;

    public static Simulation parseSimulationFile(String filename) {
        try {
            List<String> rows = Files.readAllLines(Paths.get(filename));

            var treasureMap = processTreasureMapFromRow(rows.get(0));
            var initialAdventurerList = new ArrayList<Adventurer>();

            for (int i = 1; i < rows.size(); i++) {
                String row = rows.get(i);

                if (row.isEmpty()) continue;
                processInstructionRow(row, treasureMap, initialAdventurerList);
            }

            return new Simulation(treasureMap, initialAdventurerList);
        } catch (IOException e) {
            System.err.println("Error when analyzing file '" + filename + "': " + e);
        }
        throw new IllegalStateException("Couldn't create simulation from file: '" + filename + "'");
    }


    private static String[] getSplitRowOf(String row) {
        return row.replaceAll("\\s", "").split(ARG_DELIMITER);
    }

    private static TreasureMap processTreasureMapFromRow(String row) {
        var splitRow = getSplitRowOf(row);
        if (splitRow.length != MAP_ARG_COUNT)
            throw new IllegalArgumentException("Invalid syntax for map row: '" + row + "'");

        var firstSymbol = splitRow[0];
        var secondSymbol = splitRow[1];
        var thirdSymbol = splitRow[2];
        char firstChar = firstSymbol.charAt(0);

        if (firstSymbol.length() > 1)
            throw new IllegalArgumentException("Invalid map symbol: '" + firstSymbol + "'");
        if (firstChar != MAP_SYMBOL)
            throw new IllegalArgumentException("Invalid symbol for map row: '" + firstChar + "'");

        var mapWidth = Integer.parseInt(secondSymbol);
        var mapHeight = Integer.parseInt(thirdSymbol);
        return new TreasureMap(mapWidth, mapHeight);
    }

    private static void processInstructionRow(String row, TreasureMap treasureMap, List<Adventurer> adventurerList) {
        var splitRow = getSplitRowOf(row);
        var firstSymbol = splitRow[0];

        if (firstSymbol.length() > 1)
            throw new IllegalArgumentException("Invalid instruction symbol: '" + firstSymbol + "'");

        switch (firstSymbol.charAt(0)) {
            case MOUNTAIN_SYMBOL:
                parseMountainRow(treasureMap, row);
                break;
            case TREASURE_SYMBOL:
                parseTreasureRow(treasureMap, row);
                break;
            case ADVENTURER_SYMBOL:
                parseAdventurerRow(adventurerList, row);
                break;
            default:
                throw new IllegalArgumentException("Unknown instruction symbol: '" + firstSymbol + "'");
        }
    }

    private static void parseMountainRow(TreasureMap treasureMap, String row) {
        var splitRow = getSplitRowOf(row);
        if (splitRow.length != MOUNTAIN_ARG_COUNT)
            throw new IllegalArgumentException("Invalid syntax for mountain row: '" + row + "'");

        var mountainX = Integer.parseInt(splitRow[1]);
        var mountainY = Integer.parseInt(splitRow[2]);
        treasureMap.putMountainAt(mountainX, mountainY);
    }

    private static void parseTreasureRow(TreasureMap treasureMap, String row) {
        var splitRow = getSplitRowOf(row);
        if (splitRow.length != TREASURE_ARG_COUNT)
            throw new IllegalArgumentException("Invalid syntax for treasure row: '" + row + "'");

        var treasureX = Integer.parseInt(splitRow[1]);
        var treasureY = Integer.parseInt(splitRow[2]);
        var treasuresAmount = Integer.parseInt(splitRow[3]);
        treasureMap.putTreasuresAt(treasureX, treasureY, treasuresAmount);
    }

    private static void parseAdventurerRow(List<Adventurer> adventurerList, String row) {
        var splitRow = getSplitRowOf(row);
        if (splitRow.length != ADVENTURER_ARG_COUNT)
            throw new IllegalArgumentException("Invalid syntax for adventurer row: '" + row + "'");

        var adventurerX = Integer.parseInt(splitRow[2]);
        var adventurerY = Integer.parseInt(splitRow[3]);
        var adventurerName = splitRow[1];
        var orientationSymbol = splitRow[4];
        var itinerarySymbol = splitRow[5];
        var adventurerOrientation = parseOrientationSymbol(orientationSymbol);
        var adventurerItinerary = parseItinerary(itinerarySymbol);

        var adventurer = new Adventurer(adventurerName, adventurerOrientation, adventurerItinerary, adventurerX, adventurerY);
        adventurerList.add(adventurer);
    }


    private static Orientation parseOrientationSymbol(String orientationSymbol) {
        if (orientationSymbol.length() > 1)
            throw new IllegalArgumentException("Invalid symbol for adventurer orientation: '" + orientationSymbol + "'");

        switch (orientationSymbol.charAt(0)) {
            case NORTH_SYMBOL -> {
                return Orientation.NORTH;
            }
            case SOUTH_SYMBOL -> {
                return Orientation.SOUTH;
            }
            case EAST_SYMBOL -> {
                return Orientation.EAST;
            }
            case WEST_SYMBOL -> {
                return Orientation.WEST;
            }
            default -> throw new IllegalArgumentException(
                    "Unknown symbol for adventurer orientation: '" + orientationSymbol + "'");
        }
    }

    private static Itinerary parseItinerary(String itineraryString) {
        var directions = itineraryString.chars().map(c -> (char) c).mapToObj(c -> {
            switch (c) {
                case FORWARD_SYMBOL -> {
                    return Direction.FORWARD;
                }
                case LEFT_SYMBOL -> {
                    return Direction.LEFT;
                }
                case RIGHT_SYMBOL -> {
                    return Direction.RIGHT;
                }
                default ->
                        throw new IllegalArgumentException("Unknown symbol found in adventurer itinerary: '" + c + "'");
            }
        }).toList();
        return new Itinerary(directions);
    }
}
