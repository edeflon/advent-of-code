package aoc.year2019.day08;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Layer {
    private List<Integer> pixels;

    /**
     * Count how many of given digits layer pixels has
     *
     * @param digit Digit to find in pixels list
     * @return Number of given digit
     */
    public int countDigits(int digit) {
        return (int) pixels.stream()
                .filter(pixel -> pixel == digit)
                .count();
    }
}
