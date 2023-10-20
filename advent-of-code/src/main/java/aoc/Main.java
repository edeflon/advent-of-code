package aoc;

import aoc.utilities.ProgramEnum;
import aoc.year2016.day06.RepetitionCode;
import aoc.year2017.day06.MemoryReallocation;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    // TODO : faire une méthode main plus propre
    public static void main(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Too few arguments : cmd <year> <day> <isTest>");
        }

        String year = args[0];
        String day = args[1];
        boolean isTest = Boolean.parseBoolean(args[2]);

        ProgramEnum programEnum = findEnumByDate(year, day);

        if (null != programEnum) {
            // Exécute le programme associé à la date donné
            programEnum.executeExerciceFuntion(isTest);
        } else {
            throw new IllegalArgumentException("No program found for the given date");
        }
    }

    public static ProgramEnum findEnumByDate(String year, String day) {
        return Arrays.stream(ProgramEnum.values())
                .filter((programEnum -> Objects.equals(year, programEnum.getYear())
                        && Objects.equals(day, programEnum.getDay())))
                .findFirst()
                .orElse(null);
    }
}
