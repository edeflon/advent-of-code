package aoc.year2017.day06;

import lombok.*;

import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class MemoryBank {
    List<Integer> blocks;

    /**
     * Redistribute the highest value block between all the blocks of the memory bank
     */
    public void redistributeBlocks() {
        // Find the index and value of the blocks with the max value
        int maxValue = Collections.max(blocks);
        int index = blocks.indexOf(maxValue);

        // Reset the highest value to 0
        blocks.set(index, 0);

        // Redistribute the max value between all the blocks
        for (int i = 0; i < maxValue; i++) {
            index = (index + 1) % blocks.size();
            blocks.set(index, blocks.get(index) + 1);
        }
    }
}
