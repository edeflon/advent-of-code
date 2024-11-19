package aoc.year2021.day08;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SevenSegmentSearch {

    public void sevenSegmentSearch(List<String> fileContent) {
        List<Display> displays = this.extractDisplays(fileContent);

        int numberOfDigits = (int) displays.stream()
                .map(Display::getOutputValues)
                .flatMap(List::stream)
                .map(Digit::dumbGetDigitValue)
                .filter(value -> 1 == value || 4 == value || 7 == value || 8 == value)
                .count();

        log.info("Digits 1, 4, 7, or 8 appear {} times.", numberOfDigits);

        int result = displays.stream()
                .peek(Display::resolveWiring)
                .map(Display::calculateDisplay)
                .mapToInt(Integer::intValue)
                .sum();

        log.info("If you add up all of the output values, you get {}.", result);
    }

    private List<Display> extractDisplays(List<String> fileContent) {
        List<Display> displays = new ArrayList<>();
        for (String line : fileContent) {
            List<String> splittedLine = List.of(line.split("\\|"));
            Pattern pattern = Pattern.compile("[a-z]+");

            Matcher uniqueSignalPatternMatcher = pattern.matcher(splittedLine.get(0));
            List<Digit> uniqueSignalPatterns = new ArrayList<>();
            while (uniqueSignalPatternMatcher.find()) {
                uniqueSignalPatterns.add(new Digit(uniqueSignalPatternMatcher.group(0)));
            }

            Matcher outputValuesMatcher = pattern.matcher(splittedLine.get(1));
            List<Digit> outputValues = new ArrayList<>();
            while (outputValuesMatcher.find()) {
                outputValues.add(new Digit(outputValuesMatcher.group(0)));
            }

            displays.add(new Display(uniqueSignalPatterns, outputValues));
        }
        return displays;
    }
}
