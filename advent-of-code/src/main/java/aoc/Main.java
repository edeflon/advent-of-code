package aoc;

import aoc.utilities.Exercice;

import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Too few arguments : cmd <year> <day> <isTest>");
        }

        String year = args[0];
        String day = args[1];
        boolean isTest = Boolean.parseBoolean(args[2]);

        Exercice exercice = findEnumByDate(year, day);

        if (null != exercice) {
            // Execute function linked to the exercice we found
            exercice.executeExerciceFuntion(isTest);
        } else {
            throw new IllegalArgumentException("No program found for the given date");
        }
    }

    /**
     *
     * @param year : year of the exercice
     * @param day : day of the exercice
     * @return exercice associated to the year and date
     */
    public static Exercice findEnumByDate(String year, String day) {
        return Arrays.stream(Exercice.values())
                .filter((exercice -> Objects.equals(year, exercice.getYear())
                        && Objects.equals(day, exercice.getDay())))
                .findFirst()
                .orElse(null);
    }
}
