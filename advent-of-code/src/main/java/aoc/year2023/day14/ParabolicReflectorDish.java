package aoc.year2023.day14;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toCollection;

public class ParabolicReflectorDish {

    private static final int CYCLES = 1000000000;

    public void totalLoadOnNorth(List<String> fileContent) {
        List<Rock> rocks = this.extractRocks(fileContent);

        // PART 1
//        List<Rock> rocksMovedNorth = rocks.stream()
//                .sorted(Comparator.comparing(rock -> rock.getPosition().y()))
//                .map(rock -> rock.moveNorth(rocks))
//                .toList();

//        long totalLoad = this.countRocks(rocksMovedNorth, fileContent.size());

        // PART 2
        int i = 0;
        List<List<Rock>> previousCycleResults = new ArrayList<>();
        do {
            previousCycleResults.add(rocks);
            rocks = this.doCycle(rocks);
            i++;
        } while (i < CYCLES && !previousCycleResults.contains(rocks));

        if (previousCycleResults.contains(rocks)) {
            int loopSize = i - previousCycleResults.indexOf(rocks);
            for (int j = 0; j < (CYCLES - i) % loopSize; j++) {
                rocks = this.doCycle(rocks);
            }
        }

        long totalLoad = this.countRocks(rocks, fileContent.size());

        System.out.println(totalLoad);
    }

    private List<Rock> extractRocks(List<String> fileContent) {
        List<Rock> rocks = new ArrayList<>();
        for (int y = 0; y < fileContent.size(); y++) {
            String line = fileContent.get(y);
            for (int x = 0; x < fileContent.size(); x++) {
                char chara = line.charAt(x);
                if (chara == '#') {
                    rocks.add(
                            new Rock(
                                    new Position(x, y),
                                    RockType.CUBE
                            )
                    );
                } else if (chara == 'O') {
                    rocks.add(
                            new Rock(
                                    new Position(x, y),
                                    RockType.ROUNDED
                            )
                    );
                }
            }
        }
        return rocks;
    }

    private long countRocks(List<Rock> rocks, int size) {
        long count = 0L;

        for (Rock rock: rocks) {
            if (rock.getType() == RockType.ROUNDED) {
                count += size - rock.getPosition().y();
            }
        }

        return count;
    }

    private List<Rock> doCycle(List<Rock> rocks) {
        List<Rock> rocksOrdered = rocks.stream()
                .sorted(Comparator.comparing(rock -> rock.getPosition().y()))
                .collect(toCollection(ArrayList::new));

        List<Rock> rocksNorth = IntStream.range(0, rocksOrdered.size())
                .mapToObj(i -> rocksOrdered.get(i).moveNorth(i, rocksOrdered))
                .sorted(Comparator.comparing(rock -> rock.getPosition().x()))
                .collect(toCollection(ArrayList::new));

        List<Rock> rocksWest = IntStream.range(0, rocksOrdered.size())
                .mapToObj(i -> rocksNorth.get(i).moveWest(i, rocksNorth))
                .sorted(Comparator.comparing(rock -> rock.getPosition().y(), Comparator.reverseOrder()))
                .collect(toCollection(ArrayList::new));

        List<Rock> rocksSouth = IntStream.range(0, rocksOrdered.size())
                .mapToObj(i -> rocksWest.get(i).moveSouth(i, rocksWest))
                .sorted(Comparator.comparing(rock -> rock.getPosition().x(), Comparator.reverseOrder()))
                .collect(toCollection(ArrayList::new));

        return IntStream.range(0, rocksOrdered.size())
                .mapToObj(i -> rocksSouth.get(i).moveEast(i, rocksSouth))
                .collect(toCollection(ArrayList::new));
    }
}
