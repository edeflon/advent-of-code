package aoc.year2023.day05;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FertilizerSeed {

    public void lowestLocationNumber(List<String> fileContent) {
        Pattern seedPattern = Pattern.compile("seeds: (?<seeds>[\\d|\\s]*)");

        // Init seeds
        List<Long> seeds = new ArrayList<>();
        Matcher seedMatcher = seedPattern.matcher(fileContent.get(0));
        if (seedMatcher.find()) {
            seeds = Stream.of(seedMatcher.group("seeds").split("\\s+"))
                    .filter(seed -> !seed.isBlank())
                    .map(Long::valueOf)
                    .toList();
        }

        List<MapData> data = new ArrayList<>();
        for (int i = 1; i < fileContent.size(); i++) {
            String line = fileContent.get(i);
            MapType maptype;
            switch (line) {
                case "soil-to-fertilizer map:" -> maptype = MapType.SOIL_TO_FERTI;
                case "fertilizer-to-water map:" -> maptype = MapType.FERTI_TO_WATER;
                case "water-to-light map:" -> maptype = MapType.WATER_TO_LIGHT;
                case "light-to-temperature map:" -> maptype = MapType.LIGHT_TO_TEMP;
                case "temperature-to-humidity map:" -> maptype = MapType.TEMP_TO_HUM;
                case "humidity-to-location map:" -> maptype = MapType.HUM_TO_LOCATION;
                default -> maptype = MapType.SEED_TO_SOIL;
            }

            while (!line.isBlank() && i < fileContent.size() - 1) {
                i++;
                line = fileContent.get(i);
                Pattern pattern = Pattern.compile("(?<destination>(\\d+)) (?<source>(\\d+)) (?<length>(\\d+))");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    long length = Long.parseLong(matcher.group("length"));
                    long startDestination = Long.parseLong(matcher.group("destination"));
                    long startSource = Long.parseLong(matcher.group("source"));
                    data.add(
                            new MapData(startDestination, startSource, length, maptype)
                    );
                }
            }
        }

        long lowest = Long.MAX_VALUE;
        for (int i = 0; i < seeds.size(); i++) {
            Long currentSeed = seeds.get(i);
            i++;
            Long seedRange = seeds.get(i);
            System.out.print("|");
            for (long j = 0L; j < seedRange; j++) {
                currentSeed += j;
                Long soil = this.findNextNumber(currentSeed, MapType.SEED_TO_SOIL, data);
                Long fertilizer = this.findNextNumber(soil, MapType.SOIL_TO_FERTI, data);
                Long water = this.findNextNumber(fertilizer, MapType.FERTI_TO_WATER, data);
                Long light = this.findNextNumber(water, MapType.WATER_TO_LIGHT, data);
                Long temp = this.findNextNumber(light, MapType.LIGHT_TO_TEMP, data);
                Long hum = this.findNextNumber(temp, MapType.TEMP_TO_HUM, data);
                Long location = this.findNextNumber(hum, MapType.HUM_TO_LOCATION, data);
                if (location < lowest) {
                    lowest = location;
                }
                System.out.print("|");
            }
            System.out.println(";");
        }

        System.out.println(lowest);
    }

    private Long findNextNumber(Long number, MapType type, List<MapData> datas) {
        Optional<MapData> res = datas.stream()
                .filter(data -> data.isNextNumber(number, type))
                .findFirst();

        if (res.isEmpty()) {
            return number;
        }
        return res.get().nextNumber(number);
    }
}
