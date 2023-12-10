package aoc.year2023.day10;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Position implements Comparable<Position> {
    int x;
    int y;

    @Override
    public int compareTo(@NotNull Position position) {
        int xCompare = Integer.compare(this.getX(), position.getX());

        if (xCompare != 0) {
            return xCompare;
        }

        return Integer.compare(this.getY(), position.getY());
    }
}
