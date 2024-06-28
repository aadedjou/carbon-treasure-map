package com.carbon.treasuremap.simulation;

import com.carbon.treasuremap.model.Orientation;

public class SimulationFileConstants {
    public final static String
            ARG_DELIMITER = "-",
            SPACED_ARG_DELIMITER = " " + ARG_DELIMITER + " ";

    public final static char
            MAP_SYMBOL = 'C',
            MOUNTAIN_SYMBOL = 'M',
            TREASURE_SYMBOL = 'T',
            ADVENTURER_SYMBOL = 'A',
            NORTH_SYMBOL = 'N',
            SOUTH_SYMBOL = 'S',
            EAST_SYMBOL = 'E',
            WEST_SYMBOL = 'W',
            FORWARD_SYMBOL = 'A',
            LEFT_SYMBOL = 'G',
            RIGHT_SYMBOL = 'D';

    public static char getOrientationSymbol(Orientation orientation) {
        switch (orientation) {
            case NORTH -> {
                return NORTH_SYMBOL;
            }
            case EAST -> {
                return EAST_SYMBOL;
            }
            case SOUTH -> {
                return SOUTH_SYMBOL;
            }
            case WEST -> {
                return WEST_SYMBOL;
            }
            default -> throw new IllegalStateException("Unknown orientation: '" + orientation + "'");
        }
    }
}
