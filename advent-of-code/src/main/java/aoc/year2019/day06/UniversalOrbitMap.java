package aoc.year2019.day06;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
public class UniversalOrbitMap {

    private List<DirectOrbit> directOrbits;

    /**
     * Record representing a direct orbit
     *
     * @param center        Name of the space object at the center of the orbit
     * @param objectInOrbit Name of the space object in orbit
     */
    private record DirectOrbit(String center, String objectInOrbit) {
    }

    /**
     * Main function of this class:
     * - retrieve data of given file
     * - calls countTotalNumberOfOrbits
     * - calls calculateOrbitalTransfers
     *
     * @param filename Name of the file containing the data
     * @throws IOException Exception thrown when an error is catch while reading the file
     */
    public void calculateOrbits(String filename) throws IOException {
        // Retrieve direct orbits given by the file data
        this.directOrbits = this.convertFileDataToSet(filename);

        // Count total number of direct and indirect orbits for each space object in orbit
        this.countTotalNumberOfOrbits();

        // Calculate how many orbital transfers it takes to go from "YOU" to "SAN"
        String startingName = "YOU";
        String destinationName = "SAN";
        this.calculateOrbitalTransfers(startingName, destinationName);
    }

    /**
     * Retrieve orbits objects from data file
     *
     * @param filename Name of the file containing the data
     * @return List of the orbits extracted of the file
     * @throws IOException Exception thrown when an error is catch while reading the file
     */
    private List<DirectOrbit> convertFileDataToSet(String filename) throws IOException {
        try (BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/inputs/" + filename))) {
            List<DirectOrbit> directOrbits = new ArrayList<>();
            String line;
            while ((line = bf.readLine()) != null) {
                Pattern pattern = Pattern.compile("(?<center>[A-Z1-9]+)\\)(?<objectInOrbit>[A-Z1-9]+)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    directOrbits.add(
                            new DirectOrbit(
                                    matcher.group("center"),
                                    matcher.group("objectInOrbit")
                            )
                    );
                }
            }
            return directOrbits;
        }
    }

    /**
     * Count total number of direct and indirect orbits between space objects given in the file data
     */
    private void countTotalNumberOfOrbits() {
        // Number of orbits (direct and indirect) per space object
        Map<String, Integer> numberOfOrbit = new HashMap<>();

        // Counting each orbit for each space object in orbit
        this.directOrbits.forEach(directOrbit -> {
            if (null == directOrbit) {
                return;
            }

            int count = this.countNumberOfOrbitsForOneSpaceObject(directOrbit);
            numberOfOrbit.put(directOrbit.objectInOrbit, count);
        });

        // Sum of all the orbits and display of result
        int totalOrbits = numberOfOrbit.values().stream().reduce(0, Integer::sum);
        log.info("Total number of orbits : {}", totalOrbits);
    }

    /**
     * Count number of direct and indirect orbits for one space object
     *
     * @param firstDirectOrbit Direct orbit of the space object in orbit
     * @return Number of orbits linked to the first space object in orbit
     */
    private int countNumberOfOrbitsForOneSpaceObject(DirectOrbit firstDirectOrbit) {
        int count = 1; // By default, every space object in orbit has 1 orbit

        DirectOrbit currentDirectOrbit = firstDirectOrbit;

        while (null != currentDirectOrbit) {
            String objectInOrbit = currentDirectOrbit.center;
            currentDirectOrbit = this.directOrbits.stream()
                    .filter(filteredDirectOrbit -> Objects.equals(filteredDirectOrbit.objectInOrbit, objectInOrbit))
                    .findFirst()
                    .orElse(null);
            if (null != currentDirectOrbit) {
                count += 1;
            }
        }

        return count;
    }

    /**
     * Calculate how many orbital transfers it takes to go from one space object to another
     *
     * @param source      Name of space object we use to start our transfers
     * @param destination Name of space object we use as our destination
     */
    private void calculateOrbitalTransfers(String source, String destination) {
        // Find paths of source and destination to COM (universal Center Of Mass)
        List<String> sourceToCom = this.findPathToCOM(source);
        List<String> destinationToCom = this.findPathToCOM(destination);

        // Find step where paths intersect
        String commonStep = destinationToCom.stream()
                .filter(sourceToCom::contains)
                .findFirst()
                .orElse(null);

        // If a common step is found, we remove it and the following ones
        if (null != commonStep) {
            this.cleanDuplicates(sourceToCom, commonStep);
            this.cleanDuplicates(destinationToCom, commonStep);
        } else {
            throw new NoSuchElementException();
        }

        // Build the final path
        List<String> orbitalTransfers = Stream.of(sourceToCom, List.of(commonStep), destinationToCom)
                        .flatMap(Collection::stream)
                        .toList();

        // Display the result
        // Minus 1 because we don't count the first object where the source was in orbit around at the beginning
        log.info("It takes "
                + (orbitalTransfers.size() - 1)
                + " orbital transfer to go from \""
                + source
                +"\" to \""
                + destination
                + "\""
        );
    }

    /**
     * Retrieve name of space object at center of direct orbit
     *
     * @param objectInOrbit Name of space object in direct orbit
     * @return Name of space object at center of direct orbit
     */
    private String findCenterOfOrbit(String objectInOrbit) {
        return this.directOrbits.stream()
                .filter(directOrbit -> Objects.equals(directOrbit.objectInOrbit, objectInOrbit))
                .map(directOrbit -> directOrbit.center)
                .findFirst()
                .orElseThrow();
    }

    /**
     * Find path from given source to COM (universal Center Of Mass)
     *
     * @param source Space object we use at starting point
     * @return List of space objects encountered while looking for COM
     */
    private List<String> findPathToCOM(String source) {
        List<String> path = new ArrayList<>();

        String spaceObject = source;
        while (!path.contains("COM")) {
            spaceObject = this.findCenterOfOrbit(spaceObject);
            path.add(spaceObject);
        }

        return path;
    }

    /**
     * Remove steps from given common step to the end of list
     *
     * @param steps     List of space objects
     * @param duplicate Space object we need to remove
     */
    private void cleanDuplicates(List<String> steps, String duplicate) {
        int index = steps.indexOf(duplicate);
        steps.subList(index, steps.size()).clear();
    }
}
