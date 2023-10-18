package aoc.year2017.day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MemoryReallocation {

    private static final Logger LOGGER = Logger.getLogger(MemoryReallocation.class.getPackage().getName());

    public void countRedistributionCycles(String filename) throws IOException {
        // Convert data of given file into list of memory banks
        MemoryBank memoryBank = this.convertFileDataToBank(filename);

        // Cycles
        List<Integer> lastState = memoryBank.getBlocks();
        Set<List<Integer>> states = new HashSet<>();
        while (states.add(lastState)) {
            memoryBank.redistributeBlocks();
            lastState = new ArrayList<>(memoryBank.getBlocks());
        }

        // How much redistribution can be done before a blocks-in-banks configuration is produced that has been seen before.
        LOGGER.log(Level.INFO, "Number of redistributions done : {0}", states.size());

        // Size of the loop
        List<Integer> currentState;
        int iteration = 0;
        do {
            memoryBank.redistributeBlocks();
            currentState = new ArrayList<>(memoryBank.getBlocks());
            iteration += 1;
        } while (!currentState.equals(lastState));

        LOGGER.log(Level.INFO, "Iterations : {0}", iteration);
    }

    /**
     * Convert data of given file into list of memory banks
     *
     * @param filename : name of the file where data is stored
     * @return list of memory banks
     * @throws IOException thrown exception when there is an error while reading the file
     */
    private MemoryBank convertFileDataToBank(String filename) throws IOException {
        MemoryBank memoryBank = new MemoryBank();
        try (FileReader fr = new FileReader("src/main/resources/inputs/2017/" + filename);
             BufferedReader bf = new BufferedReader(fr)) {
            String line;
            if ((line = bf.readLine()) != null) {
                memoryBank.setBlocks(Arrays.stream(line.split("\\s+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList()));
            }
            return memoryBank;
        }
    }
}
