package aoc.utilities;

import aoc.year2016.day06.RepetitionCode;
import aoc.year2017.day06.MemoryReallocation;
import aoc.year2018.day06.ChronalCoordinates;
import aoc.year2019.day06.UniversalOrbitMap;
import aoc.year2021.day06.LanternfishSimulation;
import lombok.Getter;

import java.io.IOException;
import java.util.function.Consumer;

public enum Exercice {
    // 2016
    REPETITION_CODE("2016", "06", filename -> {
        try {
            new RepetitionCode().recoverMessages(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }),

    // 2017
    MEMORY_REALLOCATION("2017", "06", filename -> {
        try {
            new MemoryReallocation().countRedistributionCyclesAndIterations(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }),

    // 2018
    CHRONAL_COORDINATES("2018", "06", filename -> {
        try {
            new ChronalCoordinates().findLargestAreaSize(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }),

    // 2019
    UNIVERSAL_ORBIT_MAP("2019", "06", filename -> {
        try {
            new UniversalOrbitMap().calculateOrbits(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }),

    // 2021
    LANTERNFISH_SIMULATION("2021", "06", filename -> {
        try {
            new LanternfishSimulation().countLanternfishsPopulation(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    });

    @Getter
    private final String year;

    @Getter
    private final String day;

    private final Consumer<String> exerciceFunction;

    Exercice(String year, String day, Consumer<String> exerciceFunction) {
        this.year = year;
        this.day = day;
        this.exerciceFunction = exerciceFunction;
    }

    /**
     * Execute the function associated to the selected exercice
     *
     * @param isTest : define on which input we start the exercice
     */
    public void executeExerciceFuntion(boolean isTest) {
        if (isTest) {
            exerciceFunction.accept(String.format("%4.4s/day_%2.2s_example.txt", year, day));
        } else {
            exerciceFunction.accept(String.format("%4.4s/day_%2.2s.txt", year, day));
        }
    }
}
