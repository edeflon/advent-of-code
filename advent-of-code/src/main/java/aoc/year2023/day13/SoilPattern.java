package aoc.year2023.day13;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class SoilPattern {
    List<String> patterns;

    public int findVerticalReflexionLine() {
        int startColumn = 0;
        boolean found = false;
        while (!found && startColumn <= this.patterns.get(0).length()) {
            found = isColumnMirrored(startColumn, 0);
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

    private String constructColumn(int index) {
        return this.patterns.stream()
                .map(pattern -> String.valueOf(pattern.charAt(index)))
                .reduce((acc, e) -> acc + e)
                .orElseThrow();
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

    private boolean isLineMirrored(int index, int delta) {
        if (index - delta < 0 || index + 1 + delta >= patterns.size()) {
            return true;
        }

        if (Objects.equals(this.patterns.get(index - delta), this.patterns.get(index + 1 + delta))) {
            return isLineMirrored(index, delta + 1);
        }
        return false;
    }
}
