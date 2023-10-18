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

    /**
     * Count and display :
     * - how many redistributions can be done before a blocks-in-banks configuration is produced that
     * has been seen before ;
     * - how many iterations there is between two instances of the same configuration.
     *
     * @param filename : name of the file where data is stored
     * @throws IOException : thrown exception when there is an error while reading the file
     */
    public void countRedistributionCyclesAndIterations(String filename) throws IOException {
        // Convert data of given file into a memory bank
        MemoryBank memoryBank = this.convertFileDataToBank(filename);

        // Blocks are redistributed until a given state is seen again
        List<Integer> lastState = memoryBank.getBlocks();
        Set<List<Integer>> states = new HashSet<>();
        while (states.add(lastState)) {
            memoryBank.redistributeBlocks();
            lastState = new ArrayList<>(memoryBank.getBlocks());
        }

        // Display how much redistribution we encountered
        LOGGER.log(Level.INFO, "Number of redistributions done : {0}", states.size());

        // Count how many iterations are done before seeing the duplicate state again
        List<Integer> currentState;
        int iteration = 0;
        do {
            memoryBank.redistributeBlocks();
            currentState = new ArrayList<>(memoryBank.getBlocks());
            iteration += 1;
        } while (!currentState.equals(lastState));

        // Display how many iterations it took
        LOGGER.log(Level.INFO, "Iterations : {0}", iteration);
    }

    /**
     * Convert data of given file into list of memory banks
     *
     * @param filename : name of the file where data is stored
     * @return list of memory banks
     * @throws IOException : thrown exception when there is an error while reading the file
     */
    private MemoryBank convertFileDataToBank(String filename) throws IOException {
        try (BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/inputs/" + filename))) {
            MemoryBank memoryBank = new MemoryBank();
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
