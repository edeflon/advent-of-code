package aoc.year2021.day06;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class LanternfishSimulation {

    /**
     * Count number of lanternfish in population (2 methods: any number of days (below 100), 256 days)
     *
     * @param filename Name of the file containing the data
     */
    public void countLanternfishsPopulation(String filename) throws IOException {
        List<Lanternfish> lanternfishs = this.convertFileDataToSet(filename);

        // Part 1
        int days = 80;
        this.countSmallLanternfishsPopulation(lanternfishs, days);

        // Part 2
        this.countLanternfishsPopulationAfter256Days(lanternfishs);
    }

    /**
     * Count number of lanternfish in population after given total number of days
     *
     * @param lanternfishs Population of lanternfish
     * @param totalDays Number of days where the lanternfish population has to live
     */
    public void countSmallLanternfishsPopulation(List<Lanternfish> lanternfishs, int totalDays) {
        int daysCounter = 0;

        while (totalDays > daysCounter) {
            List<Lanternfish> babies = new ArrayList<>();

            lanternfishs.forEach(lanternfish -> {
                if (0 == lanternfish.getReproductiveTimer()) {
                    babies.add(lanternfish.reproduce());
                }
                lanternfish.aDayPass();
            });

            lanternfishs.addAll(babies);

            daysCounter++;
        }

        log.info("There will be {} lanterfishs after {} days.", lanternfishs.size(), totalDays);
    }

    /**
     * Count number of lanternfish in population after 256 days
     *
     * @param lanternfishs Population of lanternfish
     */
    public void countLanternfishsPopulationAfter256Days(List<Lanternfish> lanternfishs) {
        int totalDays = 256;
        int daysCounter = 0;

//        while (totalDays > daysCounter) {
//            List<Lanternfish> babies = new ArrayList<>();
//
//            lanternfishs.forEach(lanternfish -> {
//                if (0 == lanternfish.getReproductiveTimer()) {
//                    babies.add(lanternfish.reproduce());
//                }
//                lanternfish.aDayPass();
//            });
//
//            lanternfishs.addAll(babies);
//
//            daysCounter++;
//        }

        log.info("There will be {} lanterfishs after {} days.", lanternfishs.size(), totalDays);
    }

    /**
     * Convert data of given file in a list of lanternfish
     *
     * @param filename Name of the file containing the data
     * @return List of lanternfish extracted of the file
     * @throws IOException Exception thrown when an error is catch while reading the file
     */
    private List<Lanternfish> convertFileDataToSet(String filename) throws IOException {
        try (BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/inputs/" + filename))) {
            List<Lanternfish> lanternfishs = new ArrayList<>();
            String line;
            while ((line = bf.readLine()) != null) {
                Pattern pattern = Pattern.compile("\\d");
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    lanternfishs.add(new Lanternfish(Integer.parseInt(matcher.group(0))));
                }
            }
            return lanternfishs;
        }
    }
}
