package aoc;

import aoc.year2016.day06.RepetitionCode;
import aoc.year2017.day06.MemoryReallocation;

import java.io.IOException;

public class Main {
    // TODO : faire une méthode main plus propre
    public static void main(String[] args) throws IOException {
        // YEAR 2016 DAY 06
        // RepetitionCode rc = new RepetitionCode();
        // rc.recoverMessages("day_06.txt");

        // YEAR 2017 DAY 06
        MemoryReallocation mr = new MemoryReallocation();
//         mr.countRedistributionCycles("day_06_example.txt");
        mr.countRedistributionCycles("day_06.txt");
    }
}
