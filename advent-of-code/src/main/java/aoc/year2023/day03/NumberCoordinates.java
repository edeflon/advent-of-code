package aoc.year2023.day03;

import lombok.Getter;

@Getter
public final class NumberCoordinates extends Coordinates {
    int number;
    int lastY; // A number can have multiple digits

    public NumberCoordinates(int x, int y, int number) {
        super(x, y);
        this.number = number;
        this.lastY = y + (String.valueOf(number).length() - 1);
    }

    public boolean isAdjacentToSymbol(SymbolCoordinates symbolCoordinates) {
        return symbolCoordinates.isAdjacentToNumber(this);
    }
}
