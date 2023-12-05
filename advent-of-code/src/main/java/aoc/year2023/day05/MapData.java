package aoc.year2023.day05;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MapData {
    Long destinationStart;
    Long sourceStart;
    Long length;
    MapType mapType;

    public boolean isNextNumber(Long number, MapType type) {
        return type == mapType && sourceStart <= number && number < sourceStart + length;
    }

    public Long nextNumber(Long number) {
        for (int i = 0; i < length; i++) {
            if (number == sourceStart + i) {
                return destinationStart + i;
            }
        }
        return destinationStart;
    }
}
