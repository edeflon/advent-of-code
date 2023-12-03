package aoc.year2023.day03;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract sealed class Coordinates permits NumberCoordinates, SymbolCoordinates {
    int x;
    int y;
}
