package aoc.year2021.day07;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class TreacheryOfWhales {

    public void howMuchFuelSpentToAlign(List<String> fileContent, boolean _isSecondPart) {
        List<Integer> positions = Arrays.stream(fileContent.get(0).split(","))
                .map(position -> Integer.parseInt(position.trim()))
                .sorted()
                .toList();

        int size = positions.size();

        int targetPosition = size % 2 == 0 ?
                (positions.get(size / 2) + positions.get(size / 2 - 1)) / 2
                : positions.get(size / 2);

        int result = positions.stream()
                .map(currentPosition -> Math.abs(targetPosition - currentPosition))
                .reduce(Integer::sum)
                .orElse(-1);

        log.info("{} fuel must be spent to align to position {}.", result, targetPosition);
    }
}
