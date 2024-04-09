package aoc.year2023.day08;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Node {
    String name;
    String leftDestination;
    String rightDestination;
    long steps;

    public String getCorrectDestination(String instruction) {
        if ("R".equals(instruction)) {
            return rightDestination;
        } else if ("L".equals(instruction)) {
            return leftDestination;
        }
        throw new IllegalArgumentException(instruction);
    }
}
