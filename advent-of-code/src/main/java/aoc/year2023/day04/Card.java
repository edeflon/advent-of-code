package aoc.year2023.day04;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public class Card implements Comparable<Card> {
    @Getter
    int cardNumber;
    List<Integer> winningNumbers;
    List<Integer> numbersDraw;

    public Card(int cardNumber, String winningNumbers, String numbersDraw) {
        this.cardNumber = cardNumber;
        this.winningNumbers = Stream.of(winningNumbers.split("\\s+"))
                .filter(string -> !string.isBlank())
                .map(Integer::parseInt)
                .toList();
        this.numbersDraw = Stream.of(numbersDraw.split("\\s+"))
                .filter(string -> !string.isBlank())
                .map(Integer::parseInt)
                .toList();
    }

    public int numberOfWinningNumbersDraw() {
        return (int) this.numbersDraw.stream()
                .filter(number -> this.winningNumbers.contains(number))
                .count();
    }

    public int score() {
        int nb = (int) this.numbersDraw.stream()
                .filter(number -> this.winningNumbers.contains(number))
                .count();

        return (int) Math.pow(2, nb - 1);
    }

    @Override
    public int compareTo(@NotNull Card o) {
        return Integer.compare(this.cardNumber, o.getCardNumber());
    }
}
