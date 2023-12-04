package aoc.year2023.day04;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Scratchcards {
    public void totalPointsWorth(List<String> fileContent) {
        TreeSet<Card> cards = this.extractCards(fileContent);
        int res = cards.stream()
                .map(Card::score)
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println(res);

        Map<Integer, Integer> numberOfCards = new HashMap<>();
        for (Card card: cards) {
            numberOfCards.put(card.getCardNumber(), 1);
        }

        cards.forEach(card -> {
            int result = card.numberOfWinningNumbersDraw();
            int numberOfCardsToAdd = numberOfCards.get(card.getCardNumber());
            for (int j = card.getCardNumber() + 1; j <= card.getCardNumber() + result && j < cards.size(); j++) {
                numberOfCards.put(j, numberOfCards.get(j) + numberOfCardsToAdd);
            }
        });

        int superRes = numberOfCards.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println(superRes);
    }

    private TreeSet<Card> extractCards(List<String> fileContent) {
        return fileContent.stream()
                .map(line -> {
                    Pattern pattern = Pattern.compile("(?<cardNumber>(\\d+)):(?<winningNumbers>(.*))\\|(?<numbersDraw>(.*))");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        return new Card(
                                Integer.parseInt(matcher.group("cardNumber")),
                                matcher.group("winningNumbers"),
                                matcher.group("numbersDraw")
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
