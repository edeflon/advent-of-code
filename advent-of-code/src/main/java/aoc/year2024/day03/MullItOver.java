package aoc.year2024.day03;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

record Multiplication(int x, int y) {
}

@Slf4j
public class MullItOver {
    public void multiplications(List<String> fileContent, boolean isSecondPart) {
        List<Multiplication> muls;
        if (!isSecondPart) {
            muls = this.parseCorrectMuls(fileContent);
        } else {
            muls = this.parseEnabledMuls(fileContent);
        }

        int sumMul = muls.stream()
                .map(mul -> mul.x() * mul.y())
                .reduce(0, Integer::sum);
        log.info("{} is the result if you add up all of the results of the multiplications.", sumMul);
    }

    private List<Multiplication> parseCorrectMuls(List<String> fileContent) {
        List<Multiplication> muls = new ArrayList<>();
        for (String line : fileContent) {
            Pattern pattern = Pattern.compile("mul\\((?<x>\\d+),(?<y>\\d+)\\)");
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                    muls.add(
                            new Multiplication(
                                    Integer.parseInt(matcher.group("x")),
                                    Integer.parseInt(matcher.group("y"))
                            )
                    );
            }
        }
        return muls;
    }

    private List<Multiplication> parseEnabledMuls(List<String> fileContent) {
        List<Multiplication> muls = new ArrayList<>();
        boolean enabled = true;

        for (String line : fileContent) {
            Pattern pattern = Pattern.compile("(mul\\((?<x>\\d+),(?<y>\\d+)\\)|do\\(\\)|don't\\(\\))");
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                if ("do()".equals(matcher.group())) {
                    enabled = true;
                } else if ("don't()".equals(matcher.group())) {
                    enabled = false;
                } else if (enabled) {
                    muls.add(
                            new Multiplication(
                                    Integer.parseInt(matcher.group("x")),
                                    Integer.parseInt(matcher.group("y"))
                            )
                    );
                }
            }
        }

        return muls;
    }
}
