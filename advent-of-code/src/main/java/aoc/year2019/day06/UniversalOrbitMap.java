package aoc.year2019.day06;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class UniversalOrbitMap {

    /**
     * Record representing a direct orbit
     *
     * @param center        Name of the space object at the center of the orbit
     * @param objectInOrbit Name of the space object in orbit
     */
    private record DirectOrbit(String center, String objectInOrbit) {
    }

    /**
     * Count total number of direct and indirect orbits between space objects given in the file data
     *
     * @param filename     Name of the file containing the data
     * @throws IOException Exception thrown when an error is catch while reading the file
     */
    public void countTotalNumberOfOrbits(String filename) throws IOException {
        // Retrieve direct orbits given by the file data
        List<DirectOrbit> directOrbits = this.convertFileDataToSet(filename);

        // Number of orbits (direct and indirect) per space object
        Map<String, Integer> numberOfOrbit = new HashMap<>();

        // Counting each orbit for each space object in orbit
        directOrbits.forEach(directOrbit -> {
            if (null == directOrbit) {
                return;
            }

            int count = this.countNumberOfOrbitsForOneSpaceObject(directOrbit, directOrbits);
            numberOfOrbit.put(directOrbit.objectInOrbit, count);
        });

        // Sum of all the orbits and display of result
        int totalOrbits = numberOfOrbit.values().stream().reduce(0, Integer::sum);
        log.info("Total number of orbits : {}", totalOrbits);
    }

    /**
     * Retrieve orbits objects from data file
     *
     * @param filename     Name of the file containing the data
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
     * Count number of direct and indirect orbits for one space object
     *
     * @param firstDirectOrbit Direct orbit of the space object in orbit
     * @param directOrbits     All direct orbits
     * @return Number of orbits linked to the first space object in orbit
     */
    private int countNumberOfOrbitsForOneSpaceObject(DirectOrbit firstDirectOrbit, List<DirectOrbit> directOrbits) {
        int count = 1; // By default, every space object in orbit has 1 orbit

        DirectOrbit currentDirectOrbit = firstDirectOrbit;

        while (null != currentDirectOrbit) {
            String objectInOrbit = currentDirectOrbit.center;
            currentDirectOrbit = directOrbits.stream()
                    .filter(filteredDirectOrbit -> Objects.equals(filteredDirectOrbit.objectInOrbit, objectInOrbit))
                    .findFirst()
                    .orElse(null);
            if (null != currentDirectOrbit) {
                count += 1;
            }
        }

        return count;
    }
}
