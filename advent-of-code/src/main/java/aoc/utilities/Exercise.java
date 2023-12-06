package aoc.utilities;

import aoc.year2016.day06.RepetitionCode;
import aoc.year2017.day06.MemoryReallocation;
import aoc.year2018.day06.ChronalCoordinates;
import aoc.year2019.day06.UniversalOrbitMap;
import aoc.year2021.day06.LanternfishSimulation;
import aoc.year2023.day02.CubeConundrum;
import aoc.year2023.day01.Trebuchet;
import aoc.year2023.day03.GearRatios;
import aoc.year2023.day04.Scratchcards;
import aoc.year2023.day05.WaitForIt;
import lombok.Getter;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.List;
import java.util.function.BiConsumer;

public enum Exercise {
    // 2016
    REPETITION_CODE("2016", "06", (fileContent, isSecondPart) ->
            new RepetitionCode().recoverMessages(fileContent, isSecondPart)
    ),

    // 2017
    MEMORY_REALLOCATION("2017", "06", (fileContent, _isSecondPart) ->
            new MemoryReallocation().countRedistributionCyclesAndIterations(fileContent)
    ),

    // 2018
    CHRONAL_COORDINATES("2018", "06", (fileContent, _isSecondPart) ->
            new ChronalCoordinates().findLargestAreaSize(fileContent)
    ),

    // 2019
    UNIVERSAL_ORBIT_MAP("2019", "06", (fileContent, _isSecondPart) ->
            new UniversalOrbitMap().calculateOrbits(fileContent)
    ),

    // 2021
    LANTERNFISH_SIMULATION("2021", "06", (fileContent, isSecondPart) ->
            new LanternfishSimulation().countLanternfishsPopulation(fileContent, isSecondPart)
    ),

    // 2023
    TREBUCHET("2023", "01", (fileContent, isSecondPart) ->
            new Trebuchet().sumOfAllCalibration(fileContent)
    ),

    CUBE_CONUNDRUM("2023", "02", (fileContent, isSecondPart) ->
            new CubeConundrum().sumIdsOfGames(fileContent)
    ),

    GEAR_RATIOS("2023", "03", (fileContent, isSecondPart) ->
            new GearRatios().sumPartNumbers(fileContent)
    ),

    SCRATCHCARDS("2023", "04", (fileContent, isSecondPart) ->
            new Scratchcards().totalPointsWorth(fileContent)
    ),

    WAIT_FOR_IT("2023", "06", (fileContent, isSecondPart) ->
            new WaitForIt().numberOfWaysBeatRecords(fileContent)
    );

    @Getter
    private final String year;

    @Getter
    private final String day;

    private final BiConsumer<List<String>, Boolean> exerciseFunction;

    Exercise(String year, String day, BiConsumer<List<String>, Boolean> exerciseFunction) {
        this.year = year;
        this.day = day;
        this.exerciseFunction = exerciseFunction;
    }

    /**
     * Execute the function associated to the selected exercise
     *
     * @param isTest       Define on which input we start the exercise
     * @param isSecondPart Define if we're testing the first or second part of exercise
     * @throws IOException Exception thrown when an error is catch while reading the file
     */
    public void executeExerciseFunction(boolean isTest, boolean isSecondPart) throws IOException {
        DataExtractor dataExtractor = new DataExtractor();
        String filename = isTest ?
                String.format("%4.4s/day_%2.2s_example.txt", year, day)
                : String.format("%4.4s/day_%2.2s.txt", year, day);
        List<String> fileContent = dataExtractor.convertFileDataToString(filename);

        if (null != fileContent) {
            exerciseFunction.accept(fileContent, isSecondPart);
        } else {
            throw new InvalidObjectException("No content found in given file.");
        }
    }
}
