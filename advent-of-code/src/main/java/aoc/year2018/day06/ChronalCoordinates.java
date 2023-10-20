package aoc.year2018.day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ChronalCoordinates {

    public void findLargestAreaSize(String filename) throws IOException {
        // Convert data input to Coordinates set
        Set<Coordinate> coordinates = this.convertFileDataToSet(filename);

        // Find the lowest and highest coordinates
        Coordinate minCoordinate = initMinCoordinate(coordinates);
        Coordinate maxCoordinate = initMaxCoordinate(coordinates);

        System.out.println(minCoordinate);
        System.out.println(maxCoordinate);

        // Calculate size of area
        Map<Coordinate, List<Coordinate>> closestLocations = new HashMap<>();
        coordinates.forEach((coordinate) -> closestLocations.put(coordinate, new ArrayList<>()));

        for (int i = minCoordinate.x(); i <= maxCoordinate.x(); i++) {
            for (int j = minCoordinate.y(); j <= maxCoordinate.y(); j++) {
                Coordinate currentCoordinate = new Coordinate(i, j);
                Map<Coordinate, Integer> distances = new HashMap<>();

                coordinates.forEach((coordinate) ->
                    distances.put(coordinate, calculateManhattanDistance(coordinate, currentCoordinate))
                );

                int min = Collections.min(distances.entrySet(), Map.Entry.comparingByValue()).getValue();
                List<Coordinate> closestCoordinates = distances.entrySet().stream()
                        .filter((distance) -> min == distance.getValue())
                        .map(Map.Entry::getKey)
                        .toList();

                if (1 == closestCoordinates.size()) {
                    List<Coordinate> cc = closestLocations.get(closestCoordinates.get(0));
                    cc.add(currentCoordinate);
                    closestLocations.put(closestCoordinates.get(0), cc);
                }
            }
        }

        // Retire des candidats les coordonnées les plus proche des "bords" (aka infinite)
        coordinates.forEach((coordinate) -> {
            List<Coordinate> closestPoints = new ArrayList<>(closestLocations.get(coordinate));
            List<Coordinate> infiniteAreas = closestPoints.stream()
                    .filter((location) -> location.x() == 0 || location.x() == 10 || location.y() == 0 || location.y() == 10)
                    .toList();

            if (!infiniteAreas.isEmpty()) {
                closestLocations.remove(coordinate);
            }
        });

        // Find the largest area
        int largestArea = closestLocations.values().stream()
                .map(List::size)
                .max(Integer::compare)
                .orElseThrow();

        System.out.println(largestArea);
    }

    public Coordinate initMinCoordinate(Set<Coordinate> coordinates) {
        int x = coordinates.stream()
                .mapToInt(Coordinate::x)
                .min()
                .orElseThrow();
        int y = coordinates.stream()
                .mapToInt(Coordinate::y)
                .min()
                .orElseThrow();
        return new Coordinate(x, y);
    }

    public Coordinate initMaxCoordinate(Set<Coordinate> coordinates) {
        int x = coordinates.stream()
                .mapToInt(Coordinate::x)
                .max()
                .orElseThrow();
        int y = coordinates.stream()
                .mapToInt(Coordinate::y)
                .max()
                .orElseThrow();
        return new Coordinate(x, y);
    }

    public int calculateManhattanDistance(Coordinate firstCoordinate, Coordinate secondCoordinate) {
        int deltaX = Math.abs(firstCoordinate.x() - secondCoordinate.x());
        int deltaY = Math.abs(firstCoordinate.y() - secondCoordinate.y());
        return deltaX + deltaY;
    }

    private Set<Coordinate> convertFileDataToSet(String filename) throws IOException {
        try (BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/inputs/" + filename))) {
            Set<Coordinate> coordinates = new HashSet<>();
            String line;
            while ((line = bf.readLine()) != null) {
                List<Integer> positions = Arrays.stream(line.split("\\s*,\\s*"))
                        .map(Integer::parseInt)
                        .toList();
                coordinates.add(new Coordinate(positions.get(0), positions.get(1)));
            }
            return coordinates;
        }
    }
}
