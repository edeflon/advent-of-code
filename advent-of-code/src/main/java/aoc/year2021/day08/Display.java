package aoc.year2021.day08;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Display {
    private List<Digit> uniqueSignalPatterns;
    private List<Digit> outputValues;
    private Map<Character, Character> correctWiring;

    Display(List<Digit> uniqueSignalPatterns, List<Digit> outputValues) {
        this.uniqueSignalPatterns = uniqueSignalPatterns;
        this.outputValues = outputValues;

        correctWiring = new HashMap<>(Map.of(
                'a', ' ', // Top
                'b', ' ', // Top left
                'e', ' ', // Bottom left
                'd', ' ', // Middle
                'c', ' ', // Top right
                'f', ' ', // Bottom right
                'g', ' '  // Bottom
        ));
    }

    /**
     * Find digit value of given number, can only find simple digits
     *
     * @param number Number of digit we want to retrieve
     * @return Digit corresponding to given number
     */
    private Digit dumbGetDigit(int number) {
        return this.uniqueSignalPatterns.stream()
                .filter(digit -> number == digit.dumbGetDigitValue())
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Correct the wiring to read numbers on displays
     */
    public void resolveWiring() {
        // Guess top segment by looking at the ones seven has in common with one
        Digit one = this.dumbGetDigit(1);
        Digit seven = this.dumbGetDigit(7);
        this.resolveTopSegment(one, seven);

        // Guess right segments by looking at one segments which are used in six segments digits
        List<Digit> sixSegmentsDigits = this.getUniqueSignalPatterns().stream()
                .filter(digit -> 6 == digit.getDisplayedSegments().length())
                .toList();
        this.resolveRightSegments(one, sixSegmentsDigits);

        // Guess top left and middle segments by looking at four segments which are used in five segments digits
        Digit four = this.dumbGetDigit(4);
        List<Digit> fiveSegmentsDigits = this.getUniqueSignalPatterns().stream()
                .filter(digit -> 5 == digit.getDisplayedSegments().length())
                .toList();
        this.resolveTopLeftAndMiddleSegments(four, fiveSegmentsDigits);

        // Guess remaining segments by looking at eight segments which are used in six segments digits
        Digit eight = this.dumbGetDigit(8);
        this.resolveBottomLeftAndBottomSegments(eight, sixSegmentsDigits);
    }

    /**
     * Resolve top segment wiring according to one and seven digits segments
     *
     * @param one   Digit number one
     * @param seven Digit number seven
     */
    private void resolveTopSegment(Digit one, Digit seven) {
        String remainingDisplayedSegments = seven.getDisplayedSegments()
                .replaceAll("[" + one.getDisplayedSegments() + "]", "");

        this.correctWiring.put('a', remainingDisplayedSegments.charAt(0));
    }

    /**
     * Resolve right segments wiring according to one digit and digits with six segments
     *
     * @param one               Digit number one
     * @param sixSegmentsDigits Digit with six segments (0, 6 and 9)
     */
    private void resolveRightSegments(Digit one, List<Digit> sixSegmentsDigits) {
        char firstSegment = one.getDisplayedSegments().charAt(0);
        char secondSegment = one.getDisplayedSegments().charAt(1);

        boolean isFirstSegmentBottomRight = this.isSegmentOnPosition(String.valueOf(firstSegment), sixSegmentsDigits);
        this.setCorrectWiring(isFirstSegmentBottomRight, 'f', 'c', firstSegment, secondSegment);
    }

    /**
     * Resolve top left and middle segments wiring according to four digit and digits with five segments
     *
     * @param four               Digit number four
     * @param fiveSegmentsDigits Digit with five segments (2, 3 and 5)
     */
    private void resolveTopLeftAndMiddleSegments(Digit four, List<Digit> fiveSegmentsDigits) {
        String remainingSegments = this.removeSegments(four.getDisplayedSegments(), List.of('c', 'f'));

        char firstSegment = remainingSegments.charAt(0);
        char secondSegment = remainingSegments.charAt(1);

        boolean isFirstSegmentMiddle = this.isSegmentOnPosition(String.valueOf(firstSegment), fiveSegmentsDigits);
        this.setCorrectWiring(isFirstSegmentMiddle, 'd', 'b', firstSegment, secondSegment);
    }

    /**
     * Resolve bottom left and middle segments wiring according to eight digit and digits with six segments
     *
     * @param eight             Digit number eight
     * @param sixSegmentsDigits Digit with six segments (0, 6 and 9)
     */
    private void resolveBottomLeftAndBottomSegments(Digit eight, List<Digit> sixSegmentsDigits) {
        String remainingSegments = this.removeSegments(eight.getDisplayedSegments(), List.of('a', 'b', 'c', 'd', 'f'));

        char firstSegment = remainingSegments.charAt(0);
        char secondSegment = remainingSegments.charAt(1);

        boolean isFirstSegmentBottom = this.isSegmentOnPosition(String.valueOf(firstSegment), sixSegmentsDigits);
        this.setCorrectWiring(isFirstSegmentBottom, 'g', 'e', firstSegment, secondSegment);
    }

    /**
     * Remove given characters to given segments
     *
     * @param segments           Segments to update
     * @param charactersToRemove List of characters to remove from given segments
     * @return Updated segments
     */
    private String removeSegments(String segments, List<Character> charactersToRemove) {
        String updatedSegments = segments;
        for (Character character: charactersToRemove) {
            updatedSegments = updatedSegments.replace(this.correctWiring.get(character), ' ');
        }
        return updatedSegments.replaceAll("\\s+","");
    }

    /**
     * Verify if given segment is on expected position
     *
     * @param firstSegment   Segment to verify
     * @param segmentsDigits Digit on which segment might be on
     * @return If segment is "on" on given digits
     */
    private boolean isSegmentOnPosition(String firstSegment, List<Digit> segmentsDigits) {
        return segmentsDigits.size() == segmentsDigits.stream()
                .filter(digit -> digit.getDisplayedSegments().contains(firstSegment))
                .count();
    }

    /**
     * Update correct wiring
     *
     * @param isFirstSegmentOnPosition Verify given wrong segments positions
     * @param firstChar                First segment to update in correct wiring
     * @param secondChar               Second segment to update in correct wiring
     * @param firstSegment             First wrong segment to assign
     * @param secondSegment            Second wrong segment to assign
     */
    private void setCorrectWiring(boolean isFirstSegmentOnPosition, char firstChar, char secondChar, char firstSegment, char secondSegment) {
        if (isFirstSegmentOnPosition) {
            this.correctWiring.put(firstChar, firstSegment);
            this.correctWiring.put(secondChar, secondSegment);
        } else {
            this.correctWiring.put(secondChar, firstSegment);
            this.correctWiring.put(firstChar, secondSegment);
        }
    }

    /**
     * Evaluate value on display
     *
     * @return Real value displayed
     */
    public int calculateDisplay() {
        return Integer.parseInt(outputValues.stream()
                .map(digit -> digit.getDigitValue(this.correctWiring))
                .map(Object::toString)
                .collect(Collectors.joining()));
    }
}
