package aoc.year2023.day10;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

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
        if (position.getX() == this.position.getX()) {
            if (position.getY() < this.position.getY()) {
                return Direction.NORTH;
            }
            return Direction.SOUTH;
        } else if (position.getY() == this.position.getY()) {
            if (position.getX() < this.position.getX() ) {
                return Direction.WEST;
            }
            return Direction.EAST;
        }
        throw new IllegalArgumentException();
    }

    public boolean isInLoop(Set<Pipe> pipes) {
        if (pipes.contains(this)) {
            return false;
        }

        return this.isInLoopHorizontally(pipes) && this.isInLoopVertically(pipes);
    }

    private boolean isInLoopVertically(Set<Pipe> pipes) {
        List<Pipe> verticalPipes = pipes.stream()
                .filter(pipe -> pipe.getPosition().getY() < this.getPosition().getY() && pipe.getPosition().getX() == this.getPosition().getX())
                .filter(pipe -> pipe.getPipeType() != PipeType.VERTICAL)
                .sorted(Comparator.comparing(Pipe::getPosition))
                .toList();

        int cornerCount = this.countCorners(verticalPipes, true);
        return (verticalPipes.size() - cornerCount) % 2 != 0;
    }

    private boolean isInLoopHorizontally(Set<Pipe> pipes) {
        List<Pipe> horizontalPipes = pipes.stream()
                .filter(pipe -> pipe.getPosition().getX() < this.getPosition().getX() && pipe.getPosition().getY() == this.getPosition().getY())
                .filter(pipe -> pipe.getPipeType() != PipeType.HORIZONTAL)
                .sorted(Comparator.comparing(Pipe::getPosition))
                .toList();

        int cornerCount = this.countCorners(horizontalPipes, false);
        return (horizontalPipes.size() - cornerCount) % 2 != 0;
    }

    private int countCorners(List<Pipe> pipes, boolean isVertical) {
        int cornerCount = 0;
        int i;
        for (i = 0; i < pipes.size() - 1; i++) {
            PipeType firstPipeType = pipes.get(i).getPipeType();
            PipeType secondPipeType = pipes.get(i + 1).getPipeType();

            if (isVertical) {
                if (isVerticalCorner(firstPipeType, secondPipeType)) {
                    cornerCount++;
                    i++;
                }
            } else {
                if (isHorizontalCorner(firstPipeType, secondPipeType)) {
                    cornerCount++;
                    i++;
                }
            }
        }
        return cornerCount;
    }

    private boolean isVerticalCorner(PipeType firstPipeType, PipeType secondPipeType) {
        return (firstPipeType == PipeType.F_BEND && secondPipeType == PipeType.J_BEND)
                || (firstPipeType == PipeType.SEVEN_BEND && secondPipeType == PipeType.L_BEND);
    }

    private boolean isHorizontalCorner(PipeType firstPipeType, PipeType secondPipeType) {
        return (firstPipeType == PipeType.F_BEND && secondPipeType == PipeType.J_BEND)
                || (firstPipeType == PipeType.L_BEND && secondPipeType == PipeType.SEVEN_BEND);
    }
}
