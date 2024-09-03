package aoc.year2018.day08;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Node {
    private List<Node> children;
    private List<Integer> metadatas;

    public int metadataSum() {
        int metadataSum = metadatas.stream()
                .mapToInt(Integer::intValue)
                .sum();

        int childrenSum = children.stream()
                .map(Node::metadataSum)
                .mapToInt(Integer::intValue)
                .sum();

        return metadataSum + childrenSum;
    }

    public int calculateValue() {
        if (children.isEmpty()) {
            return this.metadataSum();
        }

        return metadatas.stream()
                .filter(metadata -> metadata <= children.size())
                .map(metadata -> children.get(metadata - 1).calculateValue())
                .reduce(0, Integer::sum);
    }
}
