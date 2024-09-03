package aoc.year2023.day16;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FloorWillBeLava {

    private int maxX;
    private int maxY;

    private record Beam(Position position, Orientation orientation) {
    }

    private record Tile(Position position, ContraptionElement element) {
    }

    public void countTilesEnergized(List<String> fileContent) {
        this.maxX = fileContent.get(0).length();
        this.maxY = fileContent.size();

        Set<Tile> tiles = this.extractTiles(fileContent);

        Set<Beam> beams = new HashSet<>();
        beams.add(new Beam(new Position(0, 0), Orientation.RIGHT));
        Set<Position> energizedPositions = new HashSet<>(Collections.singleton(new Position(0, 0)));

        while (!beams.isEmpty()) {
            Set<Beam> nextBeams = new HashSet<>();
            for (Beam beam : beams) {
                Set<Beam> currentBeams = this.nextBeams(beam, tiles);
                energizedPositions.addAll(this.extractEnergizedPositions(beam, nextBeams));
                nextBeams.addAll(
                        currentBeams.stream()
                                .filter(next -> !energizedPositions.contains(next.position()))
                                .collect(Collectors.toSet())
                );
            }
            beams = new HashSet<>(nextBeams);
        }

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (energizedPositions.contains(new Position(x, y))) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }

        // TODO : count positions
        System.out.println(energizedPositions.size());
    }

    private Set<Position> extractEnergizedPositions(Beam beam, Set<Beam> nextBeams) {
        Set<Position> energizedPositions = new HashSet<>();
        for (Beam next : nextBeams) {
            if (beam.position() == next.position()) {
                energizedPositions.add(next.position());
            } else if (beam.position().x() == next.position().x()) {
                int x = beam.position().x();
                int start = 0;
                int end = 0;
                if (beam.position().y() > next.position().y()) {
                    start = next.position.y();
                    end = beam.position.y();
                } else {
                    start = beam.position.y();
                    end = next.position.y();
                }
                energizedPositions.addAll(IntStream.rangeClosed(start, end)
                        .mapToObj(y -> new Position(x, y))
                        .collect(Collectors.toSet()));
            } else if (beam.position().y() == next.position().y()) {
                int y = beam.position().y();
                int start = 0;
                int end = 0;
                if (beam.position().x() > next.position().x()) {
                    start = next.position.x();
                    end = beam.position.x();
                } else {
                    start = beam.position.x();
                    end = next.position.x();
                }
                energizedPositions.addAll(IntStream.rangeClosed(start, end)
                        .mapToObj(x -> new Position(x, y))
                        .collect(Collectors.toSet()));
            }
        }
        return energizedPositions;
    }

    private Set<Tile> extractTiles(List<String> fileContent) {
        Set<Tile> tiles = new HashSet<>();

        for (int y = 0; y < fileContent.size(); y++) {
            String line = fileContent.get(y);
            for (int x = 0; x < line.length(); x++) {
                String character = String.valueOf(line.charAt(x));
                if (character.equals(".")) {
                    continue;
                }

                tiles.add(
                        new Tile(
                                new Position(x, y),
                                ContraptionElement.getByCharacter(character)
                        )
                );
            }
        }

        return tiles;
    }

    private Set<Beam> nextBeams(Beam beam, Set<Tile> tiles) {
        Set<Beam> nextBeams = new HashSet<>();
        if (beam.orientation().equals(Orientation.UP)) {
            tiles.stream()
                    .filter(tile -> tile.position().y() < beam.position().y() && tile.position().x() == beam.position().x())
                    .max(Comparator.comparing(tile -> tile.position().y()))
                    .ifPresentOrElse(tile -> this.getOrientationsBySymbol(tile.element().getCharacter(), beam.orientation())
                                    .forEach(orientation -> nextBeams.add(new Beam(tile.position(), orientation))),
                            () -> nextBeams.add(new Beam(new Position(beam.position().x(), 0), beam.orientation())));
        }
        if (beam.orientation().equals(Orientation.DOWN)) {
            tiles.stream()
                    .filter(tile -> tile.position().y() > beam.position().y() && tile.position().x() == beam.position().x())
                    .min(Comparator.comparing(tile -> tile.position().y()))
                    .ifPresentOrElse(tile -> this.getOrientationsBySymbol(tile.element().getCharacter(), beam.orientation())
                                    .forEach(orientation -> nextBeams.add(new Beam(tile.position(), orientation))),
                            () -> nextBeams.add(new Beam(new Position(beam.position().x(), maxY), beam.orientation())));
        }
        if (beam.orientation().equals(Orientation.LEFT)) {
            tiles.stream()
                    .filter(tile -> tile.position().y() == beam.position().y() && tile.position().x() < beam.position().x())
                    .max(Comparator.comparing(tile -> tile.position().x()))
                    .ifPresentOrElse(tile -> this.getOrientationsBySymbol(tile.element().getCharacter(), beam.orientation())
                                    .forEach(orientation -> nextBeams.add(new Beam(tile.position(), orientation))),
                            () -> nextBeams.add(new Beam(new Position(0, beam.position().y()), beam.orientation())));
        }
        if (beam.orientation().equals(Orientation.RIGHT)) {
            tiles.stream()
                    .filter(tile -> tile.position().y() == beam.position().y() && tile.position().x() > beam.position().x())
                    .min(Comparator.comparing(tile -> tile.position().x()))
                    .ifPresentOrElse(tile -> this.getOrientationsBySymbol(tile.element().getCharacter(), beam.orientation())
                                    .forEach(orientation -> nextBeams.add(new Beam(tile.position(), orientation))),
                            () -> nextBeams.add(new Beam(new Position(maxX, beam.position().y()), beam.orientation())));
        }

        return nextBeams;
    }

    private Set<Orientation> getOrientationsBySymbol(String symbol, Orientation orientation) {
        return switch (ContraptionElement.getByCharacter(symbol)) {
            case MIRROR_ANTI -> {
                if (orientation == Orientation.UP) {
                    yield Set.of(Orientation.LEFT);
                } else if (orientation == Orientation.DOWN) {
                    yield Set.of(Orientation.RIGHT);
                } else if (orientation == Orientation.LEFT) {
                    yield Set.of(Orientation.UP);
                } else if (orientation == Orientation.RIGHT) {
                    yield Set.of(Orientation.DOWN);
                }
                throw new IllegalArgumentException();
            }
            case MIRROR_SLASH -> {
                if (orientation == Orientation.UP) {
                    yield Set.of(Orientation.RIGHT);
                } else if (orientation == Orientation.DOWN) {
                    yield Set.of(Orientation.LEFT);
                } else if (orientation == Orientation.LEFT) {
                    yield Set.of(Orientation.DOWN);
                } else if (orientation == Orientation.RIGHT) {
                    yield Set.of(Orientation.UP);
                }
                throw new IllegalArgumentException();
            }
            case SPLITTER_H -> {
                if (orientation == Orientation.LEFT || orientation == Orientation.RIGHT) {
                    yield Set.of(orientation);
                } else {
                    yield Set.of(Orientation.LEFT, Orientation.RIGHT);
                }
            }
            case SPLITTER_V -> {
                if (orientation == Orientation.UP || orientation == Orientation.DOWN) {
                    yield Set.of(orientation);
                } else {
                    yield Set.of(Orientation.UP, Orientation.DOWN);
                }
            }
        };
    }
}
