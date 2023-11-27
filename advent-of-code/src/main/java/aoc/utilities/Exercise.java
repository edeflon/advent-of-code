package aoc.utilities;

import aoc.year2016.day06.RepetitionCode;
import aoc.year2017.day06.MemoryReallocation;
import aoc.year2018.day06.ChronalCoordinates;
import aoc.year2019.day06.UniversalOrbitMap;
import aoc.year2021.day06.LanternfishSimulation;
import aoc.year2022.day06.TuningTrouble;
import lombok.Getter;

import java.io.IOException;
import java.util.function.BiConsumer;

// FIXME : corriger les try-catch
public enum Exercise {
    // 2016
    REPETITION_CODE("2016", "06", (filename, _isSecondPart) -> {
        try {
            new RepetitionCode().recoverMessages(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }),

    // 2017
    MEMORY_REALLOCATION("2017", "06", (filename, _isSecondPart) -> {
        try {
            new MemoryReallocation().countRedistributionCyclesAndIterations(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }),

    // 2018
    CHRONAL_COORDINATES("2018", "06", (filename, _isSecondPart) -> {
        try {
            new ChronalCoordinates().findLargestAreaSize(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }),

    // 2019
    UNIVERSAL_ORBIT_MAP("2019", "06", (filename, _isSecondPart) -> {
        try {
            new UniversalOrbitMap().calculateOrbits(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }),

    // 2021
    LANTERNFISH_SIMULATION("2021", "06", (filename, isSecondPart) -> {
        try {
            new LanternfishSimulation().countLanternfishsPopulation(filename, isSecondPart);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }),

    // 2022
    TUNING_TROUBLE("2022", "06", (filename, isSecondPart) -> {
        try {
            new TuningTrouble().countCharactersBeforeStartOfPacket(filename, isSecondPart);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    });

    @Getter
    private final String year;

    @Getter
    private final String day;

    private final BiConsumer<String, Boolean> exerciseFunction;

    Exercise(String year, String day, BiConsumer<String, Boolean> exerciseFunction) {
        this.year = year;
        this.day = day;
        this.exerciseFunction = exerciseFunction;
    }

    /**
     * Execute the function associated to the selected exercise
     *
     * @param isTest : define on which input we start the exercise
     */
    public void executeExerciseFunction(boolean isTest, boolean isSecondPart) {
        if (isTest) {
            exerciseFunction.accept(String.format("%4.4s/day_%2.2s_example.txt", year, day), isSecondPart);
        } else {
            exerciseFunction.accept(String.format("%4.4s/day_%2.2s.txt", year, day), isSecondPart);
        }
    }
}
