package aoc.year2023.day07;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Hand implements Comparable<Hand> {
    String cards;
    int bid;
    @Setter
    HandType type;

    @Override
    public int compareTo(@NotNull Hand hand) {
        if (this.getType() != hand.getType()) {
            return this.getType().getValue() - hand.getType().getValue();
        }

        Map<String, Integer> cardValues = new HashMap<>();
        cardValues.put("A", 12);
        cardValues.put("K", 11);
        cardValues.put("Q", 10);
        cardValues.put("T", 9);
        cardValues.put("9", 8);
        cardValues.put("8", 7);
        cardValues.put("7", 6);
        cardValues.put("6", 5);
        cardValues.put("5", 4);
        cardValues.put("4", 3);
        cardValues.put("3", 2);
        cardValues.put("2", 1);
        cardValues.put("J", 0);

        for (int i = 0; i < this.getCards().length(); i++) {
            int cardValue1 = cardValues.get(String.valueOf(this.getCards().charAt(i)));
            int cardValue2 = cardValues.get(String.valueOf(hand.getCards().charAt(i)));
            if (cardValue1 > cardValue2) {
                return 1;
            } else if (cardValue1 < cardValue2) {
                return -1;
            }
        }
        return 0;
    }
}
