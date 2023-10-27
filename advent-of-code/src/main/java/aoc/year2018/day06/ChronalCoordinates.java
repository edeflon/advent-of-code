package aoc.year2018.day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChronalCoordinates {

    private static final Logger LOGGER = Logger.getLogger(ChronalCoordinates.class.getPackage().getName());

    private record Coordinate(
            int x,
            int y
    ) {
    }

    /**
     * Find the largest area size around a given coordinate
     *
     * @param filename Name of the file containing the data
     * @throws IOException Exception thrown when an error is catch while reading the file
     */
    public void findLargestAreaSize(String filename) throws IOException {
        // Convert data input to Coordinates set
        Set<Coordinate> coordinates = this.convertFileDataToSet(filename);

        // Find the lowest and highest coordinates
        Coordinate minCoordinate = initMinCoordinate(coordinates);
        Coordinate maxCoordinate = initMaxCoordinate(coordinates);

        // Create a map to associate Coordinate with the closest points
        Map<Coordinate, List<Coordinate>> closestLocations = new HashMap<>();
        coordinates.forEach((coordinate) -> closestLocations.put(coordinate, new ArrayList<>()));

        for (int x = minCoordinate.x(); x <= maxCoordinate.x(); x++) {
            for (int y = minCoordinate.y(); y <= maxCoordinate.y(); y++) {
                Coordinate currentCoordinate = new Coordinate(x, y);

                // Calculate distance between current coordinate and each one of coordinates set
                Map<Coordinate, Integer> distances = new HashMap<>();
                coordinates.forEach((coordinate) ->
                        distances.put(coordinate, calculateManhattanDistance(coordinate, currentCoordinate))
                );

                // Retrieve minimal distance between current coordinate and others
                int minDistance = Collections.min(distances.entrySet(), Map.Entry.comparingByValue()).getValue();

                // Retrieve the closest coordinates of current coordinate
                List<Coordinate> closestCoordinates = distances.entrySet().stream()
                        .filter((distance) -> minDistance == distance.getValue())
                        .map(Map.Entry::getKey)
                        .toList();

                // If only one Coordinate is found, we register the current point in its close locations
                if (1 == closestCoordinates.size()) {
                    closestLocations.get(closestCoordinates.get(0)).add(currentCoordinate);
                }
            }
        }

        // Remove Coordinate with infinite areas
        coordinates.forEach((coordinate) -> {
            List<Coordinate> closestPoints = new ArrayList<>(closestLocations.get(coordinate));
            List<Coordinate> infiniteAreas = closestPoints.stream()
                    .filter((location) ->
                            location.x() == minCoordinate.x() ||
                                    location.x() == maxCoordinate.x() ||
                                    location.y() == minCoordinate.y() ||
                                    location.y() == maxCoordinate.y())
                    .toList();

            if (!infiniteAreas.isEmpty()) {
                closestLocations.remove(coordinate);
            }
        });

        // Find the largest area around the remaining Coordinates
        int largestArea = closestLocations.values().stream()
                .map(List::size)
                .max(Integer::compare)
                .orElseThrow();

        // Display the size of the largest area
        LOGGER.log(Level.INFO, "Size of the largest area: {0}", largestArea);
    }

    /**
     * Find the lowest coordinate possible
     *
     * @param coordinates Set of coordinates
     * @return Lowest coordinate according to the given set
     */
    private Coordinate initMinCoordinate(Set<Coordinate> coordinates) {
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

    /**
     * Find the highest coordinate possible
     *
     * @param coordinates Set of coordinates
     * @return Highest coordinate according to the given set
     */
    private Coordinate initMaxCoordinate(Set<Coordinate> coordinates) {
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

    /**
     * Calculate distance between given coordinates
     *
     * @param firstCoordinate  Coordinate (x, y)
     * @param secondCoordinate Coordinate (x, y)
     * @return Distance between given coordinates
     */
    private int calculateManhattanDistance(Coordinate firstCoordinate, Coordinate secondCoordinate) {
        int deltaX = Math.abs(firstCoordinate.x() - secondCoordinate.x());
        int deltaY = Math.abs(firstCoordinate.y() - secondCoordinate.y());
        return deltaX + deltaY;
    }

    /**
     * Convert data of given file in a set of coordinates
     *
     * @param filename Name of the file containing the data
     * @return Set of the coordinates extracted of the file
     * @throws IOException Exception thrown when an error is catch while reading the file
     */
    private Set<Coordinate> convertFileDataToSet(String filename) throws IOException {
        try (BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/inputs/" + filename))) {
            Set<Coordinate> coordinates = new HashSet<>();
            String line;
            while ((line = bf.readLine()) != null) {
                Pattern pattern = Pattern.compile("(?<x>\\d+), (?<y>\\d+)");
                Matcher matcher = pattern.matcher(line);
                if(matcher.find()) {
                    int x = Integer.parseInt(matcher.group("x"));
                    int y = Integer.parseInt(matcher.group("y"));
                    coordinates.add(new Coordinate(x, y));
                }

            }
            return coordinates;
        }
    }
}
