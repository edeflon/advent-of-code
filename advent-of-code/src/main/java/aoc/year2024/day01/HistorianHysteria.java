package aoc.year2024.day01;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

record Locations(
        List<Integer> firstLocationIds,
        List<Integer> secondLocationIds
) {}

@Slf4j
public class HistorianHysteria {
    public void totalDistanceBetweenLists(List<String> fileContent) {
        Locations locations = this.parseToLocations(fileContent);

        locations.firstLocationIds().sort(Integer::compareTo);
        locations.secondLocationIds().sort(Integer::compareTo);

        int sum = 0;
        for (int i = 0; i < locations.firstLocationIds().size(); i++) {
            sum += Math.abs(locations.firstLocationIds().get(i) - locations.secondLocationIds().get(i));
        }
        log.info("{}", sum);

        int similarityScore = 0;
        for (Integer id: locations.firstLocationIds()) {
            int numberOfAppearances = (int) locations.secondLocationIds().stream()
                    .filter(locationId -> Objects.equals(id, locationId))
                    .count();
            similarityScore += id * numberOfAppearances;
        }
        log.info("{}", similarityScore);
    }

    private Locations parseToLocations(List<String> fileContent) {
        List<Integer> firstLocationIds = new ArrayList<>();
        List<Integer> secondLocationIds = new ArrayList<>();

        for (String line: fileContent) {
            List<String> ids = List.of(line.split("\\s+"));
            firstLocationIds.add(Integer.valueOf(ids.get(0)));
            secondLocationIds.add(Integer.valueOf(ids.get(1)));
        }

        return new Locations(firstLocationIds, secondLocationIds);
    }
}
