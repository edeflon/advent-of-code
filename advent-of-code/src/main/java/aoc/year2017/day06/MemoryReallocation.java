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
        List<MemoryBank> memoryBanks = this.convertFileDataToBanks(filename);

        // Cycles
        boolean loopDetected = false;
        String lastState = "";
        Set<String> states = new HashSet<>();
        while (!loopDetected) {
            // Finds the memory bank with the most blocks (if tie : lowest id)
            this.redistributeBlocks(memoryBanks);

            // Continue
            StringBuilder stringBuilder = new StringBuilder();
            for (MemoryBank memoryBank : memoryBanks) {
                stringBuilder.append(memoryBank.getBlocks());
            }
            lastState = stringBuilder.toString();

            if (!states.add(lastState)) {
                loopDetected = true;
            }
        }

        // How much redistribution can be done before a blocks-in-banks configuration is produced that has been seen before.
        LOGGER.log(Level.INFO, "Number of redistributions done : {0}", states.size());
        LOGGER.log(Level.INFO, "Last state : {0}", lastState);

        // Size of the loop
        String currentState = "";
        int iteration = 0;
        do {
            iteration += 1;

            this.redistributeBlocks(memoryBanks);

            // Continue
            StringBuilder stringBuilder = new StringBuilder();
            for (MemoryBank memoryBank : memoryBanks) {
                stringBuilder.append(memoryBank.getBlocks());
            }
            currentState = stringBuilder.toString();
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
    private List<MemoryBank> convertFileDataToBanks(String filename) throws IOException {
        List<MemoryBank> memoryBanks = new ArrayList<>();
        try (FileReader fr = new FileReader("src/main/resources/inputs/2017/" + filename);
             BufferedReader bf = new BufferedReader(fr)) {
            String line;
            if((line = bf.readLine()) != null){
                List<Integer> allBlocks = Arrays.stream(line.split("\\s+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                for(int i = 0; i < allBlocks.size(); i++) {
                    MemoryBank memoryBank = new MemoryBank(i, allBlocks.get(i));
                    memoryBanks.add(memoryBank);
                }
            }
            return memoryBanks;
        }
    }

    private void redistributeBlocks(List<MemoryBank> memoryBanks) {
        MemoryBank selectedBank = memoryBanks.stream()
                .max(Comparator.comparing(MemoryBank::getBlocks))
                .orElseThrow();

        // Remove all the blocks from the selected blocks
        int blocksToRedistribute = selectedBank.getBlocks();
        selectedBank.setBlocks(0);

        // Moves to the next (by index)
        int id = selectedBank.getIndex();
        for (int i = 0; i < blocksToRedistribute; i++) {
            id = (id + 1) % memoryBanks.size();
            // Insert one block
            memoryBanks.get(id).addBlock();
        }
    }
}
