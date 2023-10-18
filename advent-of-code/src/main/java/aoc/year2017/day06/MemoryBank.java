package aoc.year2017.day06;

import lombok.*;

import java.util.Collections;
import java.util.List;

@Setter
@Getter
public class MemoryBank {
    List<Integer> blocks;

    public void redistributeBlocks() {
        int max = Collections.max(blocks);
        int index = blocks.indexOf(max);

        // Remove all the blocks from the selected blocks
        blocks.set(index, 0);

        // Moves to the next (by index)
        for (int i = 0; i < max; i++) {
            index = (index + 1) % blocks.size();
            // Insert one block
            blocks.set(index, blocks.get(index) + 1);
        }
    }
}
