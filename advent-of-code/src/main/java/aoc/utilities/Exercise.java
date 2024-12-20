package aoc.utilities;

import aoc.year2016.day06.RepetitionCode;
import aoc.year2016.day07.InternetProtocolVersion7;
import aoc.year2017.day06.MemoryReallocation;
import aoc.year2017.day08.YouLikeRegisters;
import aoc.year2018.day06.ChronalCoordinates;
import aoc.year2018.day08.MemoryManeuver;
import aoc.year2018.day07.SumOfItsParts;
import aoc.year2019.day06.UniversalOrbitMap;
import aoc.year2019.day08.SpaceImageFormat;
import aoc.year2020.day07.HandyHaversacks;
import aoc.year2020.day08.HandheldHalting;
import aoc.year2021.day06.LanternfishSimulation;
import aoc.year2021.day07.TreacheryOfWhales;
import aoc.year2021.day08.SevenSegmentSearch;
import aoc.year2022.day06.TuningTrouble;
import aoc.year2023.day02.CubeConundrum;
import aoc.year2023.day01.Trebuchet;
import aoc.year2023.day03.GearRatios;
import aoc.year2023.day04.Scratchcards;
import aoc.year2023.day06.WaitForIt;
import aoc.year2023.day05.FertilizerSeed;
import aoc.year2023.day07.CamelCards;
import aoc.year2023.day08.HauntedWasteland;
import aoc.year2023.day09.MirageMaintenance;
import aoc.year2023.day10.PipeMaze;
import aoc.year2023.day11.CosmicExpansion;
import aoc.year2023.day12.HotSprings;
import aoc.year2023.day13.PointOfIncidence;
import aoc.year2023.day14.ParabolicReflectorDish;
import aoc.year2023.day15.LensLibrary;
import aoc.year2023.day16.FloorWillBeLava;
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

    // 2016
    INTERNET_PROTOCOL_VERSION_7("2016", "07", (fileContent, _isSecondPart) ->
            new InternetProtocolVersion7().countIpsSupportingTls(fileContent)
    ),

    // 2017
    MEMORY_REALLOCATION("2017", "06", (fileContent, _isSecondPart) ->
            new MemoryReallocation().countRedistributionCyclesAndIterations(fileContent)
    ),

    YOU_LIKE_REGISTERS("2017", "08", (fileContent, _isSecondPart) ->
            new YouLikeRegisters().largestValueInAnyRegister(fileContent)
    ),

    // 2018
    CHRONAL_COORDINATES("2018", "06", (fileContent, _isSecondPart) ->
            new ChronalCoordinates().findLargestAreaSize(fileContent)
    ),

    SUM_OF_ITS_PARTS("2018", "07", (fileContent, _isSecondPart) ->
            new SumOfItsParts().stepOrderExecution(fileContent)
    ),

    MEMORY_MANEUVER("2018", "08", (fileContent, _isSecondPart) ->
            new MemoryManeuver().sumAllMetadatas(fileContent)
    ),

    // 2019
    UNIVERSAL_ORBIT_MAP("2019", "06", (fileContent, _isSecondPart) ->
            new UniversalOrbitMap().calculateOrbits(fileContent)
    ),

    SPACE_IMAGE_FORMAT("2019", "08", (fileContent, _isSecondPart) ->
            new SpaceImageFormat().findLayerAndMultipliedDigits(fileContent)
    ),

    // 2020
    HANDY_HAVERSACKS("2020", "07", (fileContent, isSecondPart) ->
            new HandyHaversacks().luggageProcessing(fileContent, isSecondPart)
    ),

    HANDHELD_HALTING("2020", "08", (fileContent, _isSecondPart) ->
            new HandheldHalting().accumulatorCalculations(fileContent)
    ),

    // 2021
    LANTERNFISH_SIMULATION("2021", "06", (fileContent, isSecondPart) ->
            new LanternfishSimulation().countLanternfishsPopulation(fileContent, isSecondPart)
    ),

    TREACHERY_OF_WHALES("2021", "07", (fileContent, isSecondPart) ->
            new TreacheryOfWhales().howMuchFuelSpentToAlign(fileContent, isSecondPart)
    ),

    SEVEN_SEGMENT_SEARCH("2021", "08", (fileContent, _isSecondPart) ->
            new SevenSegmentSearch().sevenSegmentSearch(fileContent)
    ),

    // 2022
    TUNING_TROUBLE("2022", "06", (filename, isSecondPart) -> {
        try {
            new TuningTrouble().countCharactersBeforeStartOfPacket(filename, isSecondPart);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }),

    // 2023
    TREBUCHET("2023", "01", (fileContent, _isSecondPart) ->
            new Trebuchet().sumOfAllCalibration(fileContent)
    ),

    CUBE_CONUNDRUM("2023", "02", (fileContent, _isSecondPart) ->
            new CubeConundrum().sumIdsOfGames(fileContent)
    ),

    GEAR_RATIOS("2023", "03", (fileContent, _isSecondPart) ->
            new GearRatios().sumPartNumbers(fileContent)
    ),

    SCRATCHCARDS("2023", "04", (fileContent, _isSecondPart) ->
            new Scratchcards().totalPointsWorth(fileContent)
    ),

    FERTILIZER_SEED("2023", "05", (fileContent, _isSecondPart) ->
            new FertilizerSeed().lowestLocationNumber(fileContent)
    ),

    WAIT_FOR_IT("2023", "06", (fileContent, _isSecondPart) ->
            new WaitForIt().numberOfWaysBeatRecords(fileContent)
    ),

    CAMEL_CARDS("2023", "07", (fileContent, _isSecondPart) ->
            new CamelCards().totalWinnings(fileContent)
    ),

    HAUNTED_WASTELAND("2023", "08", (fileContent, _isSecondPart) ->
            new HauntedWasteland().stepsRequiredToDestination(fileContent)
    ),

    MIRAGE_MAINTENANCE("2023", "09", (fileContent, isSecondPart) ->
            new MirageMaintenance().sumOfExtrapolatedValues(fileContent, isSecondPart)
    ),

    PIPE_MAZE("2023", "10", (fileContent, _isSecondPart) ->
            new PipeMaze().stepsToFarthestPoint(fileContent)
    ),

    COSMIC_EXPANSION("2023", "11", (fileContent, isSecondPart) ->
            new CosmicExpansion().lengthSum(fileContent, isSecondPart)
    ),

    HOT_SPRINGS("2023", "12", (fileContent, _isSecondPart) ->
            new HotSprings().sumOfCounts(fileContent)
    ),

    POINT_OF_INCIDENCE("2023", "13", (fileContent, _isSecondPart) ->
            new PointOfIncidence().numberOfSummarizedNotes(fileContent)
    ),

    PARABOLIC_REFLECTOR_DISH("2023", "14", (fileContent, _isSecondPart) ->
            new ParabolicReflectorDish().totalLoadOnNorth(fileContent)
    ),

    LENS_LIBRARY("2023", "15", (fileContent, _isSecondPart) ->
            new LensLibrary().sumOfResults(fileContent)
    ),

    FLOOR_WILL_BE_LAVA("2023", "16", (fileContent, _isSecondPart) ->
            new FloorWillBeLava().countTilesEnergized(fileContent)
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
