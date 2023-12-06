package aoc.year2023.day05;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor
@Getter
public class MapData {
    Long destinationStart;
    Long sourceStart;
    Long length;
    MapType mapType;

    public Optional<Long> nextNumber(Long number) {
        if (sourceStart <= number && number < sourceStart + length) {
            return Optional.of(destinationStart + (number - sourceStart));
        }
        return Optional.empty();
    }
}
