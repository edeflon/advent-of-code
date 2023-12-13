package aoc.year2023.day13;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class SoilPattern {
    List<String> patterns;

    public List<SoilPattern> generateAlternatives() {
        List<SoilPattern> alternativePatterns = new ArrayList<>();

        for (int i = 0; i < patterns.size(); i++) {
            for (int j = 0; j < patterns.get(i).length(); j++) {
                SoilPattern soilPattern = new SoilPattern(new ArrayList<>(patterns));
                StringBuilder newLine = new StringBuilder(soilPattern.patterns.get(i));
                newLine.setCharAt(
                        j,
                        this.replaceCharacter(soilPattern.patterns.get(i).charAt(j))
                );
                soilPattern.patterns.set(i, newLine.toString());
                alternativePatterns.add(soilPattern);
            }
        }

        alternativePatterns.add(this);
        return alternativePatterns;
    }

    public char replaceCharacter(char character) {
        return character == '#' ? '.' : '#';
    }

    public int findVerticalReflexionLine() {
        int startColumn = 0;
        boolean found = false;
        while (!found && startColumn <= this.patterns.get(0).length()) {
            found = isColumnMirrored(startColumn, 0);
            startColumn++;
        }
        return startColumn;
    }

    public int findHorizontalReflexionLine() {
        int startColumn = 0;
        boolean found = false;
        while (!found && startColumn < this.patterns.size()) {
            found = isLineMirrored(startColumn, 0);
            startColumn++;
        }
        return startColumn;
    }

    private boolean isColumnMirrored(int index, int delta) {
        if (index - delta < 0 || index + 1 + delta >= this.patterns.get(0).length()) {
            return true;
        }

        String firstColumn = this.constructColumn(index - delta);
        String secondColumn = this.constructColumn(index + 1 + delta);
        if (Objects.equals(firstColumn, secondColumn)) {
            return isColumnMirrored(index, delta + 1);
        }
        return false;
    }

    private boolean isLineMirrored(int index, int delta) {
        if (index - delta < 0 || index + 1 + delta >= patterns.size()) {
            return true;
        }

        if (Objects.equals(this.patterns.get(index - delta), this.patterns.get(index + 1 + delta))) {
            return isLineMirrored(index, delta + 1);
        }
        return false;
    }

    private String constructColumn(int index) {
        return this.patterns.stream()
                .map(pattern -> String.valueOf(pattern.charAt(index)))
                .reduce(String::concat)
                .orElseThrow();
    }
}
