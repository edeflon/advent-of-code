package aoc.year2017.day06;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class MemoryReallocation {

    /**
     * Count and display :
     * - how many redistributions can be done before a blocks-in-banks configuration is produced that
     * has been seen before ;
     * - how many iterations there is between two instances of the same configuration.
     *
     * @param fileContent Content of the file to process
     */
    public void countRedistributionCyclesAndIterations(List<String> fileContent) {
        // Convert data of given file into a memory bank
        MemoryBank memoryBank = this.convertFileContentToBank(fileContent);

        // Blocks are redistributed until a given state is seen again
        List<Integer> lastState = memoryBank.getBlocks();
        Set<List<Integer>> states = new HashSet<>();
        while (states.add(lastState)) {
            memoryBank.redistributeBlocks();
            lastState = new ArrayList<>(memoryBank.getBlocks());
        }

        // Display how much redistribution we encountered
        log.info("Number of redistributions done : {}", states.size());

        // Count how many iterations are done before seeing the duplicate state again
        List<Integer> currentState;
        int iteration = 0;
        do {
            memoryBank.redistributeBlocks();
            currentState = new ArrayList<>(memoryBank.getBlocks());
            iteration += 1;
        } while (!currentState.equals(lastState));

        // Display how many iterations it took
        log.info("Iterations : {}", iteration);
    }

    /**
     * Convert content file into list of memory banks
     *
     * @param fileContent File content to convert
     * @return List of memory banks
     */
    private MemoryBank convertFileContentToBank(List<String> fileContent) {
        MemoryBank memoryBank = new MemoryBank();
        if (1 == fileContent.size()) {
            memoryBank.setBlocks(
                    Arrays.stream(fileContent.get(0).split("\\s+"))
                            .map(Integer::parseInt)
                            .collect(Collectors.toCollection(ArrayList::new))
            );
        }
        return memoryBank;
    }
}
