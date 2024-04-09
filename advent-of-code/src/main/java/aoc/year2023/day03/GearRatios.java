package aoc.year2023.day03;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class GearRatios {
    public void sumPartNumbers(List<String> fileContent) {
        Set<NumberCoordinates> numbersCoordinates = this.extractNumbersCoordinates(fileContent);
        Set<SymbolCoordinates> symbolsCoordinates = this.extractSymbolsCoordinates(fileContent);

        // Remove numbers not adjacent to symbol
        int sumOfPartNumbers = numbersCoordinates.stream()
                .filter(numberCoordinates -> this.isAdjacentToSymbol(numberCoordinates, symbolsCoordinates))
                // Sum remaining numbers
                .map(NumberCoordinates::getNumber)
                .mapToInt(Integer::intValue)
                .sum();

        // Display result
        log.info("{}", sumOfPartNumbers);

        // Gear ratio
        int gearRatio = this.calculateGearRatio(numbersCoordinates, symbolsCoordinates);

        log.info("Gear ratio: {}", gearRatio);
    }

    private Set<NumberCoordinates> extractNumbersCoordinates(List<String> fileContent) {
        Set<NumberCoordinates> numberCoordinates = new HashSet<>();

        Pattern numberPattern = Pattern.compile("\\d");
        for (int x = 0; x < fileContent.size(); x++) {
            StringBuilder number = new StringBuilder();
            for (int y = 0; y < fileContent.get(x).length(); y++) {
                Matcher matcher = numberPattern.matcher(String.valueOf(fileContent.get(x).charAt(y)));
                if (matcher.find()) {
                    number.append(matcher.group());
                    if (y == fileContent.get(x).length() - 1) {
                        numberCoordinates.add(
                                new NumberCoordinates(
                                        x,
                                        y - (number.toString().length()),
                                        Integer.parseInt(number.toString()))
                        );
                    }
                } else if (!number.toString().isEmpty()) {
                    numberCoordinates.add(
                            new NumberCoordinates(
                                    x,
                                    y - (number.toString().length()),
                                    Integer.parseInt(number.toString()))
                    );
                    number = new StringBuilder();
                }
            }
        }

        return numberCoordinates;
    }

    private Set<SymbolCoordinates> extractSymbolsCoordinates(List<String> fileContent) {
        Set<SymbolCoordinates> symbolCoordinates = new HashSet<>();

        Pattern symbolPattern = Pattern.compile("[$&+=@#*%\\/-]");
        for (int x = 0; x < fileContent.size(); x++) {
            for (int y = 0; y < fileContent.get(x).length(); y++) {
                Matcher matcher = symbolPattern.matcher(String.valueOf(fileContent.get(x).charAt(y)));
                if (matcher.find()) {
                    symbolCoordinates.add(
                            new SymbolCoordinates(x, y, matcher.group())
                    );
                }
            }
        }

        return symbolCoordinates;
    }

    private boolean isAdjacentToSymbol(NumberCoordinates numberCoordinates, Set<SymbolCoordinates> symbolsCoordinates) {
        return !symbolsCoordinates.stream()
                .filter(symbolCoordinates -> symbolCoordinates.isAdjacentToNumber(numberCoordinates))
                .toList().isEmpty();
    }

    private int calculateGearRatio(Set<NumberCoordinates> numbersCoordinates, Set<SymbolCoordinates> symbolsCoordinates) {
        Set<SymbolCoordinates> starSymbols = symbolsCoordinates.stream()
                .filter(symbolCoordinates -> symbolCoordinates.getSymbol().equals("*"))
                .collect(Collectors.toSet());

        int gearRatio = 0;

        for (SymbolCoordinates starSymbol: starSymbols) {
            List<NumberCoordinates> adjacentNumbers = numbersCoordinates.stream()
                    .filter(numberCoordinates -> numberCoordinates.isAdjacentToSymbol(starSymbol))
                    .toList();
            if (adjacentNumbers.size() == 2) {
                gearRatio = gearRatio + (adjacentNumbers.get(0).getNumber() * adjacentNumbers.get(1).getNumber());
            }
        }

        return gearRatio;
    }
}
