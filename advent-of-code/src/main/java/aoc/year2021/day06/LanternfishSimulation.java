package aoc.year2021.day06;

import lombok.extern.slf4j.Slf4j;

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
     * @param fileContent Content of the file to process
     */
    public void countLanternfishsPopulation(List<String> fileContent, boolean isSecondPart) {
        // Retrieve lanternfish timers from file content
        List<Integer> lanternfishTimers = this.convertFileContentToSet(fileContent);

        Map<Integer, Long> lanternfishs = new HashMap<>();
        for (int timer : lanternfishTimers) {
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
     * Convert file content to a list of lanternfish
     *
     * @param fileContent File content to convert
     * @return List of lanternfish timers (int) extracted of the file
     */
    private List<Integer> convertFileContentToSet(List<String> fileContent) {
        List<Integer> lanternfishTimers = new ArrayList<>();
        for (String content : fileContent) {
            Pattern pattern = Pattern.compile("\\d");
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                lanternfishTimers.add(Integer.parseInt(matcher.group(0)));
            }
        }
        return lanternfishTimers;
    }
}
