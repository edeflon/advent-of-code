package aoc.year2020.day07;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Bag {
    private String type;
    private Map<Bag, Integer> containableBags;

    public Bag(String type) {
        this.type = type;
        this.containableBags = new HashMap<>();
    }

    /**
     * Evaluate if current bag can contain bag with given type
     *
     * @param bagType Type of bag we need to contain
     * @return Status if current bag can contain given bag type or not
     */
    public boolean canContainBag(String bagType) {
        for (Bag subBag : this.getContainableBags().keySet()) {
            if (bagType.equals(subBag.getType()) || subBag.canContainBag(bagType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Count how many bags are inside the bag
     *
     * @param counter Number of bags inside the given bag
     * @return Number of bags inside the given bag
     */
    public int countBagsInside(int counter) {
        return counter + this.getContainableBags().entrySet()
                .stream()
                .map(entry -> entry.getValue() + entry.getValue() * entry.getKey().countBagsInside(counter))
                .reduce(0, Integer::sum);
    }
}
