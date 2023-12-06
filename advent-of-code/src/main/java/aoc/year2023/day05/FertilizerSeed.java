package aoc.year2023.day05;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Slf4j
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

        // PART 2
        record Seed(Long value, Long range) {
        }

        Set<Seed> seedsWithRange = new HashSet<>();
        for (int i = 0; i < seeds.size(); i += 2) {
            seedsWithRange.add(new Seed(seeds.get(i), seeds.get(i + 1)));
        }

        Long lowest = seedsWithRange.parallelStream()
                .map(seed -> LongStream.range(seed.value, seed.value + seed.range)
                        .parallel()
                        .map(s -> this.getLocation(s, data))
                        .min()
                )
                .filter(OptionalLong::isPresent)
                .map(OptionalLong::getAsLong)
                .sorted()
                .findFirst().orElseThrow();

        log.info("{}", lowest);
    }

    private Long getLocation(Long seed, List<MapData> data) {
        Long soil = this.findNextNumber(seed, data.stream().filter(d -> d.mapType == MapType.SEED_TO_SOIL).toList());
        Long fertilizer = this.findNextNumber(soil, data.stream().filter(d -> d.mapType == MapType.SOIL_TO_FERTI).toList());
        Long water = this.findNextNumber(fertilizer, data.stream().filter(d -> d.mapType == MapType.FERTI_TO_WATER).toList());
        Long light = this.findNextNumber(water, data.stream().filter(d -> d.mapType == MapType.WATER_TO_LIGHT).toList());
        Long temp = this.findNextNumber(light, data.stream().filter(d -> d.mapType == MapType.LIGHT_TO_TEMP).toList());
        Long hum = this.findNextNumber(temp, data.stream().filter(d -> d.mapType == MapType.TEMP_TO_HUM).toList());
        return this.findNextNumber(hum, data.stream().filter(d -> d.mapType == MapType.HUM_TO_LOCATION).toList());
    }

    private Long findNextNumber(Long number, List<MapData> datas) {
        Optional<Long> res = datas.stream()
                .map(data -> data.nextNumber(number))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

        return res.orElse(number);
    }
}
