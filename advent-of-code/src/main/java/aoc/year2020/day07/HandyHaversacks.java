package aoc.year2020.day07;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class HandyHaversacks {
    public void luggageProcessing(List<String> fileContent, boolean isSecondPart) {
        String bagType = "shiny gold";
        Set<Bag> bags = extractBags(fileContent);

        if (!isSecondPart) {
            long nbOfBagColors = bags.stream()
                    .filter(bag -> bag.canContainBag(bagType))
                    .count();

            log.info("{} bag colors can eventually contain at least one {} bag.", nbOfBagColors, bagType);
        } else {
            int nbOfIndividualBagsInside = bags.stream()
                    .filter(bag -> bagType.equals(bag.getType()))
                    .findFirst()
                    .map(bag -> bag.howManyBagsInside())
                    .orElseThrow();

            log.info("{} individual bags are required inside your single {} bag", nbOfIndividualBagsInside, bagType);
        }
    }

    /**
     * Extract Bag from given file content
     *
     * @param fileContent file content as a list of String
     * @return Set of Bag
     */
    private Set<Bag> extractBags(List<String> fileContent) {
        Map<String, Bag> bagsByName = new HashMap<>();
        for (String line : fileContent) {
            Pattern pattern = Pattern.compile("(?<parentBag>.+) bags contain (?<bags>.*)\\.");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String type = matcher.group("parentBag");

                bagsByName.putIfAbsent(type, new Bag(type));
                Bag parentBag = bagsByName.getOrDefault(type, null);

                List<String> bags = List.of(
                        matcher.group("bags")
                                .replace("bags", "")
                                .replace("bag", "")
                                .split(",")
                );

                for (String bag : bags) {
                    Pattern bagPattern = Pattern.compile("(?<number>\\d+) (?<bagType>(\\w+) (\\w+))");
                    Matcher bagMatcher = bagPattern.matcher(bag);
                    if (bagMatcher.find()) {
                        int number = Integer.parseInt(bagMatcher.group("number"));
                        String bagType = bagMatcher.group("bagType");

                        bagsByName.putIfAbsent(bagType, new Bag(bagType));
                        Bag subBag = bagsByName.getOrDefault(bagType, null);
                        if (null != parentBag && null != subBag) {
                            parentBag.getContainableBags().put(subBag, number);
                        }
                    }
                }
            }
        }

        return new HashSet<>(bagsByName.values());
    }
}
