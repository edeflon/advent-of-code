package aoc.year2023.day13;

import java.util.ArrayList;
import java.util.List;

public class PointOfIncidence {

    public void numberOfSummarizedNotes(List<String> fileContent) {
        List<SoilPattern> soilPatterns = this.extractSoilPatterns(fileContent);

        int rows = 0;
        int columns = 0;

        for (SoilPattern soilPattern : soilPatterns) {
            int localRows = soilPattern.findHorizontalReflexionLine();
            if (localRows < soilPattern.patterns.size()) {
                rows += localRows;
                continue;
            }
            int localColumns = soilPattern.findVerticalReflexionLine();
            columns += localColumns;
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
}
