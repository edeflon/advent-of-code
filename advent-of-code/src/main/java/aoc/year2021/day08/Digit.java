package aoc.year2021.day08;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class Digit {
    private String displayedSegments;

    /**
     * Find digit value according to number of segments on
     * @return Digit value
     */
    public int dumbGetDigitValue() {
        int numberOfSegmentsOn = displayedSegments.length();

        return switch (numberOfSegmentsOn) {
            case 2:
                yield 1;
            case 3:
                yield 7;
            case 4:
                yield 4;
            case 7:
                yield 8;
            default:
                yield -1;
        };
    }

    /**
     * Find digit value according to number of segments on and wiring
     *
     * @param correctWiring Wiring to read numbers
     * @return Digit value
     */
    public int getDigitValue(Map<Character, Character> correctWiring) {
        int numberOfSegmentsOn = displayedSegments.length();

        return switch (numberOfSegmentsOn) {
            case 2:
                yield 1;
            case 3:
                yield 7;
            case 4:
                yield 4;
            case 5:
                if (displayedSegments.contains(String.valueOf(correctWiring.get('c')))
                        && displayedSegments.contains(String.valueOf(correctWiring.get('e')))) {
                    yield 2;
                } else if (displayedSegments.contains(String.valueOf(correctWiring.get('c')))
                        && displayedSegments.contains(String.valueOf(correctWiring.get('f')))) {
                    yield 3;
                } else if (displayedSegments.contains(String.valueOf(correctWiring.get('b')))
                        && displayedSegments.contains(String.valueOf(correctWiring.get('f')))) {
                    yield 5;
                }
                throw new IllegalArgumentException("Impossible to determine if it's a 2, 3, 5: " + displayedSegments);
            case 6:
                if (!displayedSegments.contains(String.valueOf(correctWiring.get('d')))) {
                    yield 0;
                } else if (!displayedSegments.contains(String.valueOf(correctWiring.get('c')))) {
                    yield 6;
                } else if (!displayedSegments.contains(String.valueOf(correctWiring.get('e')))) {
                    yield 9;
                }
                throw new IllegalArgumentException("Impossible to determine if it's a 0, 6 or 9: " + displayedSegments);
            case 7:
                yield 8;
            default:
                yield -1;
        };
    }
}
