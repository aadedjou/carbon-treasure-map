package com.carbon.treasuremap;

import com.carbon.treasuremap.simulation.SimulationFileParser;
import com.carbon.treasuremap.simulation.SimulationFileWriter;

public class Main {
    public static void main(String[] args) {
        var filename = args[0];
        var simulation = SimulationFileParser.parseSimulationFile(filename);

        simulation.runAllItineraries();

        SimulationFileWriter.writeSimulationOutputFile(simulation, filename);
    }
}
