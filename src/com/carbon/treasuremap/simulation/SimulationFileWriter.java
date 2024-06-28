package com.carbon.treasuremap.simulation;

import com.carbon.treasuremap.model.TreasureMap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.carbon.treasuremap.simulation.SimulationFileConstants.*;

public class SimulationFileWriter {
    private static final String FILE_SUFFIX = "-output";

    public static void writeSimulationOutputFile(Simulation simulation, String basename) {
        var outputFilename = Objects.requireNonNull(basename) + FILE_SUFFIX;
        var allRows = generateAllFormattedRows(simulation);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {
            for (String row : allRows) {
                writer.write(row);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error when writing file '" + outputFilename + "': " + e);
        }
    }

    private static List<String> generateAllFormattedRows(Simulation simulation) {
        var treasureMap = simulation.getTreasureMap();
        return Stream.of(
                List.of(generateMapRow(treasureMap)),
                generateMountainRows(treasureMap),
                generateTreasureRows(treasureMap),
                generateAdventurerRows(simulation)
        ).flatMap(Collection::stream).toList();
    }

    private static String formattedRowOf(Object... objects) {
        return Arrays.stream(objects)
                .map(Object::toString)
                .collect(Collectors.joining(SPACED_ARG_DELIMITER));
    }

    private static String generateMapRow(TreasureMap treasureMap) {
        return formattedRowOf(MAP_SYMBOL, treasureMap.getWidth(), treasureMap.getHeight());
    }

    private static List<String> generateMountainRows(TreasureMap treasureMap) {
        return treasureMap.getAllMountainsCoordinates().stream().map(c -> formattedRowOf(
                MOUNTAIN_SYMBOL,
                c.getX(),
                c.getY())
        ).toList();
    }

    private static List<String> generateTreasureRows(TreasureMap treasureMap) {
        return treasureMap.getAllTreasures().stream().map(entry -> {
            var coordinates = entry.getKey();
            var treasureTile = entry.getValue();

            return formattedRowOf(
                    TREASURE_SYMBOL,
                    coordinates.getX(),
                    coordinates.getY(),
                    treasureTile.getTreasuresCount()
            );
        }).toList();
    }

    private static List<String> generateAdventurerRows(Simulation simulation) {
        return simulation.getAllAdventurers().stream().map(a -> formattedRowOf(
                String.valueOf(ADVENTURER_SYMBOL),
                String.valueOf(a.getName()),
                String.valueOf(a.getPosition().getX()),
                String.valueOf(a.getPosition().getY()),
                String.valueOf(SimulationFileConstants.getOrientationSymbol(a.getOrientation())),
                String.valueOf(a.getPickedTreasures())
        )).toList();
    }
}
