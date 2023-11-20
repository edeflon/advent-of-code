package aoc;

import aoc.utilities.Exercise;

import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Too few arguments : cmd <year> <day> <isTest> <isSecondPart>");
        }

        String year = args[0];
        String day = args[1];
        boolean isTest = Boolean.parseBoolean(args[2]);
        boolean isSecondPart = args.length == 4 && Boolean.parseBoolean(args[3]);

        Exercise exercise = findEnumByDate(year, day);

        if (null != exercise) {
            // Execute function linked to the exercise we found
            exercise.executeExerciseFunction(isTest, isSecondPart);
        } else {
            throw new IllegalArgumentException("No program found for the given date");
        }
    }

    /**
     * Retrieve exercise linked to given date
     *
     * @param year : year of the exercise
     * @param day : day of the exercise
     * @return exercise associated to the year and date
     */
    public static Exercise findEnumByDate(String year, String day) {
        return Arrays.stream(Exercise.values())
                .filter((exercise -> Objects.equals(year, exercise.getYear())
                        && Objects.equals(day, exercise.getDay())))
                .findFirst()
                .orElse(null);
    }
}
