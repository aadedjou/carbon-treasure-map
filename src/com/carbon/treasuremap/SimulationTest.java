package com.carbon.treasuremap;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {
    final static String
            FILEPATH = "./test-maps/",
            ERR_PREFIX = "map-err-",
            FILENAME_DUPLICATE_COORDINATES = FILEPATH + ERR_PREFIX + "duplicate-cx",
            FILENAME_ITEM_OUT_OF_BOUNDS = FILEPATH + ERR_PREFIX + "item-oob",
            FILENAME_ADVENTURER_OUT_OF_BOUNDS = FILEPATH + ERR_PREFIX + "adv-oob",
            FILENAME_UNKNOWN_SYMBOL = FILEPATH + ERR_PREFIX + "unknown-symbol",
            FILENAME_MISSING_ARGUMENTS = FILEPATH + ERR_PREFIX + "missing-args",
            FILENAME_INVALID_SIZE = FILEPATH + ERR_PREFIX + "invalid-size",
            FILENAME_INVALID_ITINERARY = FILEPATH + ERR_PREFIX + "invalid-itinerary";

    @Test
    @Tag(FILENAME_DUPLICATE_COORDINATES)
    public void testDuplicateCoordinatesCase() {
        assertThrows(IllegalArgumentException.class, () -> Main.main(new String[]{FILENAME_DUPLICATE_COORDINATES}));
    }

    @Test
    @Tag(FILENAME_ITEM_OUT_OF_BOUNDS)
    public void testItemOutOfBoundsCase() {
        assertThrows(IndexOutOfBoundsException.class, () -> Main.main(new String[]{FILENAME_ITEM_OUT_OF_BOUNDS}));
    }

    @Test
    @Tag(FILENAME_ADVENTURER_OUT_OF_BOUNDS)
    public void testItineraryOutOfBoundsCase() {
        assertThrows(IndexOutOfBoundsException.class, () -> Main.main(new String[]{FILENAME_ADVENTURER_OUT_OF_BOUNDS}));
    }

    @Test
    @Tag(FILENAME_INVALID_SIZE)
    public void testInvalidMapSizeCase() {
        assertThrows(IllegalArgumentException.class, () -> Main.main(new String[]{FILENAME_INVALID_SIZE}));
    }

    @Test
    @Tag(FILENAME_INVALID_ITINERARY)
    public void testInvalidItineraryCase() {
        assertThrows(IllegalArgumentException.class, () -> Main.main(new String[]{FILENAME_INVALID_ITINERARY}));
    }

    @Test
    @Tag(FILENAME_UNKNOWN_SYMBOL)
    public void testUnknownSymbolCase() {
        assertThrows(IllegalArgumentException.class, () -> Main.main(new String[]{FILENAME_UNKNOWN_SYMBOL}));
    }

    @Test
    @Tag(FILENAME_MISSING_ARGUMENTS)
    public void testMissingArgumentsCase() {
        assertThrows(IllegalArgumentException.class, () -> Main.main(new String[]{FILENAME_UNKNOWN_SYMBOL}));
    }
}
