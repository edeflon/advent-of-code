package aoc.year2023.day11;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CosmicExpansion {

    int expansion = 999999;

    public void lengthSum(List<String> fileContent, boolean isSecondPart) {
        this.expansion = isSecondPart ? 999999 : 1;

        Set<Galaxy> galaxies = this.extractGalaxies(fileContent);

        Set<Galaxy> expandedGalaxies = new HashSet<>();
        galaxies.forEach(galaxy -> {
            int missingColumns = this.countMissingColumnsFrom(galaxy.getX(), galaxies);
            int missingRows = this.countMissingRowsFrom(galaxy.getY(), galaxies);
            expandedGalaxies.add(
                    new Galaxy(
                            galaxy.getX() + missingColumns,
                            galaxy.getY() + missingRows
                    )
            );
        });

        Set<Galaxy> galaxiesToCalculate = new HashSet<>(expandedGalaxies);
        long lengthSum = expandedGalaxies.stream()
                .map(galaxy -> galaxy.calculateAllDistances(galaxiesToCalculate))
                .reduce(0L, Long::sum);

        System.out.println(lengthSum);
    }

    private Set<Galaxy> extractGalaxies(List<String> fileContent) {
        Set<Galaxy> galaxies = new HashSet<>();
        for (int y = 0; y < fileContent.size(); y++) {
            String line = fileContent.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (!String.valueOf(line.charAt(x)).equals("#")) {
                    continue;
                }
                galaxies.add(new Galaxy(x, y));
            }
        }
        return galaxies;
    }

    private int countMissingColumnsFrom(int x, Set<Galaxy> galaxies) {
        if (x == 0) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < x; i++) {
            int finalI = i;
            long nbGalaxiesOnColumn = galaxies.stream()
                    .filter(galaxy -> galaxy.getX() < x)
                    .filter(galaxy -> galaxy.getX() == finalI)
                    .count();
            count = nbGalaxiesOnColumn == 0 ? (count + this.expansion) : count;
        }
        return count;
    }

    private int countMissingRowsFrom(int y, Set<Galaxy> galaxies) {
        if (y == 0) {
            return 0;
        }

        int count = 0;
        for (int i = 1; i < y; i++) {
            int finalI = i;
            long nbGalaxiesOnColumn = galaxies.stream()
                    .filter(galaxy -> galaxy.getY() < y)
                    .filter(galaxy -> galaxy.getY() == finalI)
                    .count();
            count = nbGalaxiesOnColumn == 0 ? (count + this.expansion) : count;
        }
        return count;
    }
}
