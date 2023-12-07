package aoc.year2023.day07;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CamelCards {

    record Hand(String cards, int bid) {
    }

    public void totalWinnings(List<String> fileContent) {
        List<Hand> hands = this.extractHands(fileContent);
        List<HandType> handTypes = List.of(
                HandType.HIGH_CARD,
                HandType.ONE_PAIR,
                HandType.TWO_PAIR,
                HandType.THREE_OF_A_KIND,
                HandType.FULL_HOUSE,
                HandType.FOUR_OF_A_KIND,
                HandType.FIVE_OF_A_KIND
        );

        List<Hand> handsByRank = this.sortHandsByRank(hands, handTypes);

        int score = this.calculateScore(handsByRank);
        System.out.println(score);
    }

    private List<Hand> extractHands(List<String> fileContent) {
        List<Hand> hands = new ArrayList<>();
        for (String line : fileContent) {
            Pattern pattern = Pattern.compile("(?<hands>([A-Z0-9]+))\\s+(?<bid>(\\d+))");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                hands.add(new Hand(
                        matcher.group("hands"),
                        Integer.parseInt(matcher.group("bid"))
                ));
            }
        }
        return hands;
    }

    private List<Hand> sortHandsByRank(List<Hand> hands, List<HandType> handTypes) {
        Map<HandType, List<Hand>> handByHandType = new EnumMap<>(
                Map.of(
                        HandType.FIVE_OF_A_KIND, new ArrayList<>(),
                        HandType.FOUR_OF_A_KIND, new ArrayList<>(),
                        HandType.FULL_HOUSE, new ArrayList<>(),
                        HandType.THREE_OF_A_KIND, new ArrayList<>(),
                        HandType.TWO_PAIR, new ArrayList<>(),
                        HandType.ONE_PAIR, new ArrayList<>(),
                        HandType.HIGH_CARD, new ArrayList<>()
                )
        );

        for (Hand hand : hands) {
            Map<String, Integer> charCount = new HashMap<>();
            List.of(hand.cards.split("")).forEach(chara -> charCount.put(chara, 0));
            List.of(hand.cards.split("")).forEach(chara -> charCount.put(chara, charCount.get(chara) + 1));

            int jokerCount = charCount.entrySet().stream()
                    .filter(chara -> Objects.equals(chara.getKey(), "J"))
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .orElse(0);

            if (jokerCount == 5) {
                handByHandType.get(HandType.FIVE_OF_A_KIND).add(hand);
                continue;
            }

            Optional<Integer> count5 = charCount.entrySet().stream()
                    .filter(chara -> chara.getValue() == 5 && !Objects.equals(chara.getKey(), "J"))
                    .map(Map.Entry::getValue)
                    .findFirst();

            if (count5.isPresent()) {
                handByHandType.get(HandType.FIVE_OF_A_KIND).add(hand);
                continue;
            }

            Optional<Integer> count4 = charCount.entrySet().stream()
                    .filter(chara -> chara.getValue() == 4 && !Objects.equals(chara.getKey(), "J"))
                    .map(Map.Entry::getValue)
                    .findFirst();

            if (count4.isPresent() && jokerCount == 1) {
                handByHandType.get(HandType.FIVE_OF_A_KIND).add(hand);
                continue;
            } else if (count4.isPresent()) {
                handByHandType.get(HandType.FOUR_OF_A_KIND).add(hand);
                continue;
            }

            Optional<Integer> count3 = charCount.entrySet().stream()
                    .filter(chara -> chara.getValue() == 3 && !Objects.equals(chara.getKey(), "J"))
                    .map(Map.Entry::getValue)
                    .findFirst();

            long count2 = charCount.entrySet().stream()
                    .filter(chara -> chara.getValue() == 2 && !Objects.equals(chara.getKey(), "J"))
                    .map(Map.Entry::getValue)
                    .count();

            if (count3.isPresent() && jokerCount > 0) {
                if (jokerCount == 2) {
                    handByHandType.get(HandType.FIVE_OF_A_KIND).add(hand);
                } else if (jokerCount == 1) {
                    handByHandType.get(HandType.FOUR_OF_A_KIND).add(hand);
                }
            } else if (count3.isPresent() && count2 == 1) {
                handByHandType.get(HandType.FULL_HOUSE).add(hand);
            } else if (count3.isPresent()) {
                handByHandType.get(HandType.THREE_OF_A_KIND).add(hand);
            } else if (count2 == 2 && jokerCount == 1) {
                handByHandType.get(HandType.FULL_HOUSE).add(hand);
            } else if (count2 == 2) {
                handByHandType.get(HandType.TWO_PAIR).add(hand);
            } else if (count2 == 1 && jokerCount > 0) {
                if (jokerCount == 3) {
                    handByHandType.get(HandType.FIVE_OF_A_KIND).add(hand);
                } else if (jokerCount == 2) {
                    handByHandType.get(HandType.FOUR_OF_A_KIND).add(hand);
                } else if (jokerCount == 1) {
                    handByHandType.get(HandType.THREE_OF_A_KIND).add(hand);
                }
            } else if (count2 == 1) {
                handByHandType.get(HandType.ONE_PAIR).add(hand);
            } else if (jokerCount > 0) {
                if (jokerCount == 4) {
                    handByHandType.get(HandType.FIVE_OF_A_KIND).add(hand);
                } else if (jokerCount == 3) {
                    handByHandType.get(HandType.FOUR_OF_A_KIND).add(hand);
                } else if (jokerCount == 2) {
                    handByHandType.get(HandType.THREE_OF_A_KIND).add(hand);
                } else if (jokerCount == 1) {
                    handByHandType.get(HandType.ONE_PAIR).add(hand);
                }
            } else {
                handByHandType.get(HandType.HIGH_CARD).add(hand);
            }
        }

        for (HandType handType : handTypes) {
            if (handByHandType.get(handType).size() < 2) {
                continue;
            }

            handByHandType.get(handType).sort(this::sortHand);
        }

        List<Hand> handsByRank = new ArrayList<>();
        for (HandType handType : handTypes) {
            handsByRank.addAll(handByHandType.get(handType));
        }

        return handsByRank;
    }

    private int sortHand(Hand hand1, Hand hand2) {
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

        for (int i = 0; i < hand1.cards().length(); i++) {
            int cardValue1 = cardValues.get(String.valueOf(hand1.cards().charAt(i)));
            int cardValue2 = cardValues.get(String.valueOf(hand2.cards().charAt(i)));
            if (cardValue1 > cardValue2) {
                return 1;
            } else if (cardValue1 < cardValue2) {
                return -1;
            }
        }
        return 0;
    }

    private int calculateScore(List<Hand> hands) {
        int score = 0;
        for (int i = 0; i < hands.size(); i++) {
            score += hands.get(i).bid() * (i + 1);
        }
        return score;
    }
}
