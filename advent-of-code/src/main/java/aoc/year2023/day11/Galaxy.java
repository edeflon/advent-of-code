package aoc.year2023.day11;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Setter
@Getter
public class Galaxy {
    int x;
    int y;

    public long calculateAllDistances(Set<Galaxy> galaxies) {
        galaxies.remove(this);
        return galaxies.stream()
                .map(this::calculateDistance)
                .reduce(0L, Long::sum);
    }

    public long calculateDistance(Galaxy secondGalaxy) {
        return Math.abs(this.getX() - secondGalaxy.getX()) + Math.abs(this.getY() - secondGalaxy.getY());
    }
}
