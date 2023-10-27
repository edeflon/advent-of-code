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

    private record Orbit(String center, String objectInOrbit) {
    }

    // TODO : documentation & clean
    public void countTotalNumberOfOrbits(String filename) throws IOException {
        List<Orbit> orbits = this.convertFileDataToSet(filename);

        // Number of orbits (direct and indirect) per space object
        Map<String, Integer> numberOfOrbit = new HashMap<>();

        orbits.forEach(orbit -> {
            if (null != orbit) {
                int count = 1; // By default, every space object has 1 object in orbit
                Orbit currentOrbit = orbit;
                while (null != currentOrbit) {
                    String objectInOrbit = currentOrbit.center;
                    currentOrbit = orbits.stream()
                            .filter(filteredOrbit -> Objects.equals(filteredOrbit.objectInOrbit, objectInOrbit))
                            .findFirst()
                            .orElse(null);
                    if (null != currentOrbit) {
                        count += 1;
                    }
                }
                numberOfOrbit.put(orbit.objectInOrbit, count);
            }
        });

        int totalOrbits = numberOfOrbit.values().stream().reduce(0, Integer::sum);

        log.info("Total number of orbits : {}", totalOrbits);
    }

    /**
     * @param filename Name of the file containing the data
     * @return List of the orbits extracted of the file
     * @throws IOException Exception thrown when an error is catch while reading the file
     */
    private List<Orbit> convertFileDataToSet(String filename) throws IOException {
        try (BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/inputs/" + filename))) {
            List<Orbit> orbits = new ArrayList<>();
            String line;
            while ((line = bf.readLine()) != null) {
                Pattern pattern = Pattern.compile("(?<center>[A-Z1-9]+)\\)(?<objectInOrbit>[A-Z1-9]+)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    orbits.add(
                            new Orbit(
                                    matcher.group("center"),
                                    matcher.group("objectInOrbit")
                            )
                    );
                }
            }
            return orbits;
        }
    }
}
