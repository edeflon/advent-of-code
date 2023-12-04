package aoc.year2023.day03;

import lombok.Getter;

@Getter
public final class SymbolCoordinates extends Coordinates {
    String symbol;

    public SymbolCoordinates(int x, int y, String symbol) {
        super(x, y);
        this.symbol = symbol;
    }

    public boolean isAdjacentToNumber(NumberCoordinates numberCoordinates) {
        int x = numberCoordinates.getX();
        if (x < this.getX() - 1 || x > this.getX() + 1) {
            return false;
        }

        for (int y = numberCoordinates.getY(); y <= numberCoordinates.getLastY(); y++) {
            if (y == this.getY() - 1 || y == this.getY() || y == this.getY() + 1) {
                return true;
            }
        }
        return false;
    }
}
