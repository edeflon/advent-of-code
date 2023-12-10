package aoc.year2023.day10;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PipeMaze {

    public void stepsToFarthestPoint(List<String> fileContent) {
        Set<Pipe> pipes = this.extractPipe(fileContent);

        // PART 1
        List<Pipe> loop = new ArrayList<>();
        Pipe currentPipe = pipes.stream()
                .filter(pipe -> pipe.getPipeType().equals(PipeType.START))
                .findFirst()
                .orElseThrow();
        do {
            loop.add(currentPipe);
            currentPipe = this.findConnectedPipes(currentPipe, pipes).stream()
                    .filter(pipe -> !loop.contains(pipe))
                    .findFirst()
                    .orElse(null);
        } while (currentPipe != null);

        int steps = loop.size() / 2;
        System.out.println(steps);
    }

    private Set<Pipe> extractPipe(List<String> fileContent) {
        Set<Pipe> pipes = new HashSet<>();
        for (int y = 0; y < fileContent.size(); y++) {
            String line = fileContent.get(y);
            for (int x = 0; x < line.length(); x++) {
                pipes.add(
                        new Pipe(
                                PipeType.getFromType(String.valueOf(line.charAt(x))),
                                new Position(x, y)
                        )
                );
            }
        }
        return pipes;
    }

    private Set<Pipe> findConnectedPipes(Pipe currentPipe, Set<Pipe> pipes) {
        return pipes.stream()
                .filter(pipe -> this.isAdjacent(currentPipe.getPosition(), pipe.getPosition()))
                .filter(pipe -> pipe.isConnectedTo(currentPipe))
                .collect(Collectors.toSet());
    }

    private boolean isAdjacent(Position a, Position b) {
        return ((b.x() - 1 == a.x() || b.x() + 1 == a.x()) && b.y() == a.y())
                || ((b.y() - 1 == a.y() || b.y() + 1 == a.y()) && b.x() == a.x());
    }
}
