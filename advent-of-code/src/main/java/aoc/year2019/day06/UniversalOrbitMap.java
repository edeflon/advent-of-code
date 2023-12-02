package aoc.year2019.day06;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

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
     * @param fileContent Content of the file to process
     */
    public void calculateOrbits(List<String> fileContent) {
        // Retrieve direct orbits given in the file content
        this.directOrbits = this.convertFileContentToSet(fileContent);

        // Count total number of direct and indirect orbits for each space object in orbit
        this.countTotalNumberOfOrbits();

        // Calculate how many orbital transfers it takes to go from "YOU" to "SAN"
        String startingName = "YOU";
        String destinationName = "SAN";
        this.calculateOrbitalTransfers(startingName, destinationName);
    }

    /**
     * Retrieve orbits objects from file content
     *
     * @param fileContent File content to convert
     * @return List of the orbits extracted of the file
     */
    private List<DirectOrbit> convertFileContentToSet(List<String> fileContent) {
        List<DirectOrbit> orbits = new ArrayList<>();
        for (String content : fileContent) {
            Pattern pattern = Pattern.compile("(?<center>[A-Z1-9]+)\\)(?<objectInOrbit>[A-Z1-9]+)");
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                orbits.add(
                        new DirectOrbit(
                                matcher.group("center"),
                                matcher.group("objectInOrbit")
                        )
                );
            }
        }
        return orbits;
    }

    /**
     * Count total number of direct and indirect orbits between space objects given in the file data
     */
    private void countTotalNumberOfOrbits() {
        // Counting each orbit for each space object in orbit
        int count = 0;
        for (@NotNull DirectOrbit directOrbit : this.directOrbits) {
            count += this.countNumberOfOrbitsForOneSpaceObject(directOrbit);
        }

        // Sum of all the orbits and display of result
        log.info("Total number of orbits : {}", count);
    }

    /**
     * Count number of direct and indirect orbits for one space object
     *
     * @param firstDirectOrbit Direct orbit of the space object in orbit
     * @return Number of orbits linked to the first space object in orbit
     */
    private int countNumberOfOrbitsForOneSpaceObject(DirectOrbit firstDirectOrbit) {
        int count = 1; // By default, every space object in orbit has 1 orbit

        Optional<DirectOrbit> currentDirectOrbit = Optional.of(firstDirectOrbit);

        while (currentDirectOrbit.isPresent()) {
            String objectInOrbit = currentDirectOrbit.get().center;
            currentDirectOrbit = this.directOrbits.stream()
                    .filter(filteredDirectOrbit -> Objects.equals(filteredDirectOrbit.objectInOrbit, objectInOrbit))
                    .findFirst();
            if (currentDirectOrbit.isPresent()) {
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
        List<String> commonSteps = destinationToCom.stream()
                .filter(sourceToCom::contains)
                .toList();

        // Remove common step and the following ones
        sourceToCom.removeAll(commonSteps);
        destinationToCom.removeAll(commonSteps);

        // Build the final path
        String firstCommonStep = commonSteps.get(0);
        List<String> orbitalTransfers = Stream.of(sourceToCom, List.of(firstCommonStep), destinationToCom)
                .flatMap(Collection::stream)
                .toList();

        // Display the result
        // Minus 1 because we don't count the first object where the source was in orbit around at the beginning
        int numberOfOrbitalTransfers = orbitalTransfers.size() - 1;
        log.info("It takes {} orbital transfer to go from \"{}\" to \"{}\".", numberOfOrbitalTransfers, source, destination);
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
                .findFirst()
                .map(directOrbit -> directOrbit.center)
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
}
