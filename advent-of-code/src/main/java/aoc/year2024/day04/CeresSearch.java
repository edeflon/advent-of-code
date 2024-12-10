package aoc.year2024.day04;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class CeresSearch {
    public void xmasCount(List<String> fileContent) {
        Set<Letter> letterMatrix = this.parseLetters(fileContent);

        // Part 1
        Set<Letter> letterXs = letterMatrix.stream()
                .filter(letter -> 'X' == letter.value())
                .collect(Collectors.toSet());

        int count = 0;
        for (Letter letterX : letterXs) {
            Set<Letter> lettersAroundX = letterMatrix.stream()
                    .filter(letter -> this.isLetterInRange(letter, letterX.position(), 3))
                    .collect(Collectors.toSet());
            count += this.countXmasWords(letterX, lettersAroundX);
        }
        log.info("XMAS appears {} times.", count);

        // Part 2
        Set<Letter> letterAs = letterMatrix.stream()
                .filter(letter -> 'A' == letter.value())
                .collect(Collectors.toSet());

        int masCount = 0;
        for (Letter letterA : letterAs) {
            Set<Letter> lettersAroundMAS = letterMatrix.stream()
                    .filter(letter -> this.isLetterInRange(letter, letterA.position(), 1))
                    .collect(Collectors.toSet());
            if (this.countMasWords(letterA, lettersAroundMAS)) {
                masCount++;
            }
        }
        log.info("X-MAS appears {} times.", masCount);
    }

    /**
     * Parse given file content to letters with their positions
     *
     * @param fileContent Input file content
     * @return Set containing position and values of each letter
     */
    private Set<Letter> parseLetters(List<String> fileContent) {
        Set<Letter> letters = new HashSet<>();

        for (int y = 0; y < fileContent.size(); y++) {
            String line = fileContent.get(y);
            for (int x = 0; x < line.length(); x++) {
                letters.add(
                        new Letter(line.charAt(x), new Position(x, y))
                );
            }
        }

        return letters;
    }

    /**
     * Determine if a letter is in range of given position
     *
     * @param letter   Letter to verify
     * @param position Position of the first letter
     * @param range    Maximum range possible
     * @return Whether the given letter is in range of given position
     */
    private boolean isLetterInRange(Letter letter, Position position, int range) {
        return letter.position() != position
                && letter.position().x() >= (position.x() - range)
                && letter.position().x() <= (position.x() + range)
                && letter.position().y() >= (position.y() - range)
                && letter.position().y() <= (position.y() + range);
    }

    /**
     * Count how many XMAS words there is in given letter matrix around given X letter
     *
     * @param letterX      Center of the letter matrix
     * @param letterMatrix All letters surrounding given X
     * @return How many XMAS words there is in given letter matrix
     */
    private int countXmasWords(Letter letterX, Set<Letter> letterMatrix) {
        return this.countXmasInLines(letterMatrix, letterX.position())
                + this.countXmasInDiagonals(letterMatrix, letterX.position());
    }

    /**
     * Count how many XMAS words there is in given letter matrix around given position on X and Y axis
     *
     * @param letters  Letter surrounding given position
     * @param position Center of matrix
     * @return How many XMAS words there is in given letter matrix around given position on X and Y axis
     */
    private int countXmasInLines(Set<Letter> letters, Position position) {
        int x = position.x();
        int y = position.y();

        StringBuilder horizontal = new StringBuilder();
        StringBuilder vertical = new StringBuilder();

        for (int i = -3; i < 4; i++) {
            if (0 == i) {
                horizontal.append('X');
                vertical.append('X');
            }
            this.findLetterInPosition(letters, new Position(x + i, y))
                    .ifPresent(letter -> horizontal.append(letter.value()));
            this.findLetterInPosition(letters, new Position(x, y + i))
                    .ifPresent(letter -> vertical.append(letter.value()));
        }

        return this.masCounter(horizontal.toString()) + this.masCounter(vertical.toString());
    }

    /**
     * Count how many XMAS words there is in given letter matrix around given position on diagonals axis
     *
     * @param letters  Letter surrounding given position
     * @param position Center of matrix
     * @return How many XMAS words there is in given letter matrix around given position on diagonals axis
     */
    private int countXmasInDiagonals(Set<Letter> letters, Position position) {
        int x = position.x();
        int y = position.y();

        StringBuilder diagonal = new StringBuilder();
        StringBuilder reverseDiagonal = new StringBuilder();

        for (int i = -3; i < 4; i++) {
            if (0 == i) {
                diagonal.append('X');
                reverseDiagonal.append('X');
            }
            this.findLetterInPosition(letters, new Position(x + i, y + i))
                    .ifPresent(letter -> diagonal.append(letter.value()));
            this.findLetterInPosition(letters, new Position(x - i, y + i))
                    .ifPresent(letter -> reverseDiagonal.append(letter.value()));
        }

        return this.masCounter(diagonal.toString()) + this.masCounter(reverseDiagonal.toString());
    }

    /**
     * Find letter value in given position
     *
     * @param letters  All letters
     * @param position Position of wanted letter
     * @return Letter in given position
     */
    private Optional<Letter> findLetterInPosition(Set<Letter> letters, Position position) {
        return letters.stream()
                .filter(letter -> letter.position().x() == position.x()
                        && letter.position().y() == position.y())
                .findFirst();
    }

    /**
     * Count how many "SAM" or "MAS" there is in given word
     *
     * @param word Word to analyze
     * @return How much "SAM" or "MAS" there is in given word
     */
    private int masCounter(String word) {
        int count = 0;
        if (word.startsWith("SAM")) {
            count++;
        }
        if (word.endsWith("MAS")) {
            count++;
        }
        return count;
    }

    /**
     * Verify if there is a MAS matrix in given letter matrix around given A letter
     *
     * @param letterA      Center of given matrix
     * @param letterMatrix All letters surrounding given A letter
     * @return True if there is a MAS matrix, else false
     */
    private boolean countMasWords(Letter letterA, Set<Letter> letterMatrix) {
        int x = letterA.position().x();
        int y = letterA.position().y();

        StringBuilder diagonal = new StringBuilder();
        StringBuilder reverseDiagonal = new StringBuilder();

        for (int i = -1; i < 2; i++) {
            if (0 == i) {
                diagonal.append('A');
                reverseDiagonal.append('A');
            }
            this.findLetterInPosition(letterMatrix, new Position(x + i, y + i))
                    .ifPresent(letter -> diagonal.append(letter.value()));
            this.findLetterInPosition(letterMatrix, new Position(x - i, y + i))
                    .ifPresent(letter -> reverseDiagonal.append(letter.value()));
        }

        return (("SAM".contentEquals(diagonal) || "MAS".contentEquals(diagonal))
                && ("SAM".contentEquals(reverseDiagonal) || "MAS".contentEquals(reverseDiagonal)));
    }
}
