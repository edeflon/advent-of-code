package aoc.year2023.day07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CamelCards {

    /**
     * Calculate total winnings with hands from game
     *
     * @param fileContent list of String data extracted from input file
     */
    public void totalWinnings(List<String> fileContent) {
        // Get list of hands, set types and order list
        List<Hand> handsWithoutTypes = this.extractHands(fileContent);
        List<Hand> hands = this.setHandsTypes(handsWithoutTypes);
        hands.sort(Hand::compareTo);

        // Calculate total winnings
        int score = 0;
        for (int i = 0; i < handsWithoutTypes.size(); i++) {
            score += hands.get(i).getBid() * (i + 1);
        }

        // Display results
        System.out.println(score);
    }

    /**
     * Extract Hands from file content
     *
     * @param fileContent list of String data extracted from input file
     * @return List of hands from game
     */
    private List<Hand> extractHands(List<String> fileContent) {
        List<Hand> hands = new ArrayList<>();
        for (String line : fileContent) {
            Pattern pattern = Pattern.compile("(?<hands>([A-Z0-9]+))\\s+(?<bid>(\\d+))");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                hands.add(new Hand(
                        matcher.group("hands"),
                        Integer.parseInt(matcher.group("bid")),
                        null
                ));
            }
        }
        return hands;
    }

    /**
     * Set Hand types
     *
     * @param hands Hands without types
     * @return Hands with types
     */
    private List<Hand> setHandsTypes(List<Hand> hands) {
        for (Hand hand : hands) {
            Map<String, Integer> charCount = new HashMap<>();
            List.of(hand.cards.split("")).forEach(chara -> charCount.put(chara, charCount.getOrDefault(chara, 0) + 1));

            int jokerCount = charCount.getOrDefault("J", 0);
            charCount.put("J", 0);

            int max = Collections.max(charCount.values()) + jokerCount;
            int secondMax = charCount.values().stream()
                    .sorted(Comparator.reverseOrder())
                    .skip(1)
                    .findFirst()
                    .orElse(0);

            switch (max) {
                case 5 -> hand.setType(HandType.FIVE_OF_A_KIND);
                case 4 -> hand.setType(HandType.FOUR_OF_A_KIND);
                case 3 -> {
                    if (secondMax == 2) {
                        hand.setType(HandType.FULL_HOUSE);
                    } else {
                        hand.setType(HandType.THREE_OF_A_KIND);
                    }
                }
                case 2 -> {
                    if (secondMax == 2) {
                        hand.setType(HandType.TWO_PAIR);
                    } else {
                        hand.setType(HandType.ONE_PAIR);
                    }
                }
                default -> hand.setType(HandType.HIGH_CARD);
            }
        }

        return hands;
    }
}
