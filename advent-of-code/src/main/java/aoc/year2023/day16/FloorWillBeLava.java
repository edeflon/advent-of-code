package aoc.year2023.day16;

import java.util.*;
import java.util.stream.Collectors;

public class FloorWillBeLava {

    private record Beam(Position position, Orientation orientation) {
    }

    private record Tile(Position position, ContraptionElement element) {
    }

    public void countTilesEnergized(List<String> fileContent) {
        Set<Tile> tiles = this.extractTiles(fileContent);

        // TODO : move beams -> return set of position energized by beam
        Set<Beam> beams = new HashSet<>();
        beams.add(new Beam(new Position(0, 0), Orientation.D));
        Set<Position> energizedPositions = new HashSet<>(Collections.singleton(new Position(0, 0)));

        while (!beams.isEmpty()) {
            beams = beams.stream()
                    .map(beam -> this.nextBeams(beam, tiles))
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
            energizedPositions.addAll(
                    beams.stream()
                            .map(Beam::position)
                            .collect(Collectors.toSet())
            );
        }

        // TODO : count positions
        System.out.println(energizedPositions.size());
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
        Optional<Tile> optElement = switch (beam.orientation) {
            case U -> tiles.stream()
                    .filter(tile -> tile.position().y() < beam.position().y() && tile.position().x() == beam.position().x())
                    .max(Comparator.comparing(tile -> tile.position().y()));
            case D -> tiles.stream()
                    .filter(tile -> tile.position().y() > beam.position().y() && tile.position().x() == beam.position().x())
                    .min(Comparator.comparing(tile -> tile.position().y()));
            case L -> tiles.stream()
                    .filter(tile -> tile.position().y() == beam.position().y() && tile.position().x() < beam.position().x())
                    .max(Comparator.comparing(tile -> tile.position().x()));
            case R -> tiles.stream()
                    .filter(tile -> tile.position().y() == beam.position().y() && tile.position().x() > beam.position().x())
                    .min(Comparator.comparing(tile -> tile.position().x()));
        };

        if (optElement.isEmpty()) {
            return Collections.emptySet();
        }

        return this.getNextBeams(beam.orientation(), optElement.get(), tiles);
    }

    private Set<Beam> getNextBeams(Orientation orientation, Tile tile, Set<Tile> tiles) {
        return switch (tile.element()) {
            case MIRROR_ANTI -> {
                yield switch (orientation) {
                    case U -> tiles.stream()
                            .filter(t -> t.position().x() - 1 == tile.position().x() && t.position().y() == tile.position().y())
                            .map(t -> new Beam(t.position(), Orientation.L))
                            .collect(Collectors.toSet());
                    case D -> tiles.stream()
                            .filter(t -> t.position().x() + 1 == tile.position().x() && t.position().y() == tile.position().y())
                            .map(t -> new Beam(t.position(), Orientation.R))
                            .collect(Collectors.toSet());
                    case L -> tiles.stream()
                            .filter(t -> t.position().x() == tile.position().x() && t.position().y() + 1 == tile.position().y())
                            .map(t -> new Beam(t.position(), Orientation.D))
                            .collect(Collectors.toSet());
                    case R -> tiles.stream()
                            .filter(t -> t.position().x() == tile.position().x() && t.position().y() - 1 == tile.position().y())
                            .map(t -> new Beam(t.position(), Orientation.U))
                            .collect(Collectors.toSet());
                };
            }
            case MIRROR_SLASH -> {
                yield switch (orientation) {
                    case U -> tiles.stream()
                            .filter(t -> t.position().x() + 1 == tile.position().x() && t.position().y() == tile.position().y())
                            .map(t -> new Beam(t.position(), Orientation.R))
                            .collect(Collectors.toSet());
                    case D -> tiles.stream()
                            .filter(t -> t.position().x() - 1 == tile.position().x() && t.position().y() == tile.position().y())
                            .map(t -> new Beam(t.position(), Orientation.L))
                            .collect(Collectors.toSet());
                    case L -> tiles.stream()
                            .filter(t -> t.position().x() == tile.position().x() && t.position().y() - 1 == tile.position().y())
                            .map(t -> new Beam(t.position(), Orientation.U))
                            .collect(Collectors.toSet());
                    case R -> tiles.stream()
                            .filter(t -> t.position().x() == tile.position().x() && t.position().y() + 1 == tile.position().y())
                            .map(t -> new Beam(t.position(), Orientation.D))
                            .collect(Collectors.toSet());
                };
            }
            case SPLITTER_H -> {
                yield switch (orientation) {
                    case U -> {
                        Set<Beam> leftBeams = tiles.stream()
                                .filter(t -> t.position().x() - 1 == tile.position().x() && t.position().y() == tile.position().y())
                                .map(t -> new Beam(t.position(), Orientation.L))
                                .collect(Collectors.toSet());
                        Set<Beam> rightBeams = tiles.stream()
                                .filter(t -> t.position().x() + 1 == tile.position().x() && t.position().y() == tile.position().y())
                                .map(t -> new Beam(t.position(), Orientation.R))
                                .collect(Collectors.toSet());
                        leftBeams.addAll(rightBeams);
                        yield leftBeams;
                    }
                    case D -> {
                        Set<Beam> leftBeams = tiles.stream()
                                .filter(t -> t.position().x() - 1 == tile.position().x() && t.position().y() == tile.position().y())
                                .map(t -> new Beam(t.position(), Orientation.L))
                                .collect(Collectors.toSet());
                        Set<Beam> rightBeams = tiles.stream()
                                .filter(t -> t.position().x() + 1 == tile.position().x() && t.position().y() == tile.position().y())
                                .map(t -> new Beam(t.position(), Orientation.R))
                                .collect(Collectors.toSet());
                        leftBeams.addAll(rightBeams);
                        yield leftBeams;
                    }
                    case L -> tiles.stream()
                            .filter(t -> t.position().x() + 1 == tile.position().x() && t.position().y() == tile.position().y())
                            .map(t -> new Beam(t.position(), Orientation.L))
                            .collect(Collectors.toSet());
                    case R -> tiles.stream()
                            .filter(t -> t.position().x() - 1 == tile.position().x() && t.position().y() == tile.position().y())
                            .map(t -> new Beam(t.position(), Orientation.R))
                            .collect(Collectors.toSet());
                };
            }
            case SPLITTER_V -> {
                yield switch (orientation) {
                    case U -> tiles.stream()
                            .filter(t -> t.position().x() == tile.position().x() && t.position().y() - 1 == tile.position().y())
                            .map(t -> new Beam(t.position(), Orientation.U))
                            .collect(Collectors.toSet());
                    case D -> tiles.stream()
                            .filter(t -> t.position().x() == tile.position().x() && t.position().y() + 1 == tile.position().y())
                            .map(t -> new Beam(t.position(), Orientation.D))
                            .collect(Collectors.toSet());
                    case R -> {
                        Set<Beam> upBeams = tiles.stream()
                                .filter(t -> t.position().x() == tile.position().x() && t.position().y() - 1 == tile.position().y())
                                .map(t -> new Beam(t.position(), Orientation.U))
                                .collect(Collectors.toSet());
                        Set<Beam> downBeams = tiles.stream()
                                .filter(t -> t.position().x() == tile.position().x() && t.position().y() + 1 == tile.position().y())
                                .map(t -> new Beam(t.position(), Orientation.D))
                                .collect(Collectors.toSet());
                        upBeams.addAll(downBeams);
                        yield upBeams;
                    }
                    case L -> {
                        Set<Beam> upBeams = tiles.stream()
                                .filter(t -> t.position().x() == tile.position().x() && t.position().y() - 1 == tile.position().y())
                                .map(t -> new Beam(t.position(), Orientation.U))
                                .collect(Collectors.toSet());
                        Set<Beam> downBeams = tiles.stream()
                                .filter(t -> t.position().x() == tile.position().x() && t.position().y() + 1 == tile.position().y())
                                .map(t -> new Beam(t.position(), Orientation.D))
                                .collect(Collectors.toSet());
                        upBeams.addAll(downBeams);
                        yield upBeams;
                    }
                };
            }
        };
    }
}
