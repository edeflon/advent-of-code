package aoc.year2017.day06;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemoryBank {
    int index;
    int blocks;

    public void addBlock() {
        this.blocks += 1;
    }
}
