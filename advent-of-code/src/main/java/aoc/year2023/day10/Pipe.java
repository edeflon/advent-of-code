package aoc.year2023.day10;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pipe {
    PipeType pipeType;
    Position position;

    public boolean isConnectedTo(Pipe pipe) {
        Direction direction = this.currentDirection(pipe.getPosition());
        if (!this.getPipeType().getDirections().contains(direction)) {
            return false;
        }

        return switch (direction) {
            case NORTH -> pipe.getPipeType().getDirections().contains(Direction.SOUTH);
            case SOUTH -> pipe.getPipeType().getDirections().contains(Direction.NORTH);
            case WEST -> pipe.getPipeType().getDirections().contains(Direction.EAST);
            case EAST -> pipe.getPipeType().getDirections().contains(Direction.WEST);
        };
    }

    private Direction currentDirection(Position position) {
        if (position.x() == this.position.x()) {
            if (position.y() < this.position.y()) {
                return Direction.NORTH;
            }
            return Direction.SOUTH;
        } else if (position.y() == this.position.y()) {
            if (position.x() < this.position.x() ) {
                return Direction.WEST;
            }
            return Direction.EAST;
        }
        throw new IllegalArgumentException();
    }
}
