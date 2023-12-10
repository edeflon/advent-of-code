package aoc.year2023.day10;

import lombok.Getter;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Getter
public enum PipeType {
    VERTICAL("|", Set.of(Direction.NORTH, Direction.SOUTH)),
    HORIZONTAL("-", Set.of(Direction.EAST, Direction.WEST)),
    L_BEND("L", Set.of(Direction.NORTH, Direction.EAST)),
    J_BEND("J", Set.of(Direction.NORTH, Direction.WEST)),
    SEVEN_BEND("7", Set.of(Direction.SOUTH, Direction.WEST)),
    F_BEND("F", Set.of(Direction.SOUTH, Direction.EAST)),
    GROUND(".", Collections.emptySet()),
    START("S", Set.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST));

    private final String type;
    private final Set<Direction> directions;

    PipeType(String type, Set<Direction> directions) {
        this.type = type;
        this.directions = directions;
    }

    public static PipeType getFromType(String type) {
        return Stream.of(PipeType.values())
                .filter(pipeType -> Objects.equals(pipeType.getType(), type))
                .findFirst()
                .orElseThrow();
    }
}
