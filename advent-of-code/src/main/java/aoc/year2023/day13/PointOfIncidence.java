package aoc.year2023.day13;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PointOfIncidence {

    public void numberOfSummarizedNotes(List<String> fileContent) {
        List<SoilPattern> soilPatterns = this.extractSoilPatterns(fileContent);

        int rows = 0;
        int columns = 0;

        for (SoilPattern soilPattern : soilPatterns) {
            Optional<Integer> localRows = this.findAlternativeHorizontalReflexionLine(soilPattern);
            if (localRows.isPresent()) {
                rows += localRows.get();
                continue;
            }
            Optional<Integer> localColumns = this.findAlternativeVerticalReflexionLine(soilPattern);
            if (localColumns.isPresent()) {
                columns += localColumns.get();
            }
        }

        int numberOfNotes = 100 * rows + columns;

        System.out.println(numberOfNotes);
    }

    private List<SoilPattern> extractSoilPatterns(List<String> fileContent) {
        List<SoilPattern> soilPatterns = new ArrayList<>();

        SoilPattern soilPattern = new SoilPattern(new ArrayList<>());
        for (String line : fileContent) {
            if (!line.isBlank()) {
                soilPattern.patterns.add(line);
            } else {
                soilPatterns.add(soilPattern);
                soilPattern = new SoilPattern(new ArrayList<>());
            }
        }

        soilPatterns.add(soilPattern);

        return soilPatterns;
    }

    private Optional<Integer> findAlternativeHorizontalReflexionLine(SoilPattern pattern) {
        int rows = pattern.findHorizontalReflexionLine();

//        Optional<Integer> alternativeRows = Optional.empty(); // Part 1
        List<SoilPattern> alternatives = pattern.generateAlternatives();
        int finalRows = rows;
        Optional<Integer> alternativeRows = alternatives.stream()
                .map(SoilPattern::findHorizontalReflexionLine)
                .filter(result -> result < pattern.patterns.size() && result != finalRows)
                .min(Integer::compareTo);

        rows = alternativeRows.orElse(rows);
        if (rows >= pattern.patterns.size()) {
            return Optional.empty();
        }

        return Optional.of(rows);
    }

    private Optional<Integer> findAlternativeVerticalReflexionLine(SoilPattern pattern) {
        int columns = pattern.findVerticalReflexionLine();

//        Optional<Integer> alternativeColumns = Optional.empty();
        List<SoilPattern> alternatives = pattern.generateAlternatives();
        int finalColumns = columns;
        Optional<Integer> alternativeColumns = alternatives.stream()
                .map(SoilPattern::findVerticalReflexionLine)
                .filter(result -> result < pattern.patterns.get(0).length() && result != finalColumns)
                .min(Integer::compareTo);

        columns = alternativeColumns.orElse(columns);
        if (columns >= pattern.patterns.get(0).length()) {
            return Optional.empty();
        }

        return Optional.of(columns);
    }
}
