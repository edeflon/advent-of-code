package aoc.year2021.day06;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class LanternfishSimulation {

    /**
     * Count number of lanternfish in population after given number of days
     *
     * @param filename Name of the file containing the data
     */
    public void countLanternfishsPopulation(String filename, boolean isSecondPart) throws IOException {
        // Retrieve data from file
        List<Integer> lanternfishTimers = this.convertFileDataToSet(filename);

        Map<Integer, Long> lanternfishs = new HashMap<>();
        for (int timer: lanternfishTimers) {
            lanternfishs.put(
                    timer,
                    lanternfishs.getOrDefault(timer, 0L) + 1
            );
        }

        int days = isSecondPart ? 256 : 80;
        for (int i = 1; i <= days; i++) {
            Map<Integer, Long> futureLanternfishs = new HashMap<>();

            futureLanternfishs.put(6, lanternfishs.getOrDefault(0, 0L));
            futureLanternfishs.put(8, lanternfishs.getOrDefault(0, 0L));

            for (int key = 7; key >= 0; key--) {
                futureLanternfishs.put(
                        key,
                        futureLanternfishs.getOrDefault(key, 0L) + lanternfishs.getOrDefault(key + 1, 0L)
                );
            }

            lanternfishs = futureLanternfishs;
        }

        long totalFish = lanternfishs.values().stream().reduce(0L, Long::sum);
        log.info("There will be {} lanternfishs after {} days.", totalFish, days);
    }

    /**
     * Convert data of given file in a list of lanternfish
     *
     * @param filename Name of the file containing the data
     * @return List of lanternfish timers (int) extracted of the file
     * @throws IOException Exception thrown when an error is catch while reading the file
     */
    private List<Integer> convertFileDataToSet(String filename) throws IOException {
        try (BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/inputs/" + filename))) {
            List<Integer> lanternfishTimers = new ArrayList<>();
            String line;
            while ((line = bf.readLine()) != null) {
                Pattern pattern = Pattern.compile("\\d");
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    lanternfishTimers.add(Integer.parseInt(matcher.group(0)));
                }
            }
            return lanternfishTimers;
        }
    }
}
