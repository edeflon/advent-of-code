package aoc.year2023.day08;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HauntedWasteland {

    public void stepsRequiredToDestination(List<String> fileContent) {
        List<String> instructions = List.of(fileContent.get(0).split(""));
        fileContent.remove(0);

        Set<Node> nodes = this.extractNodes(fileContent);

        Set<Node> travelNodes = nodes.stream()
                .filter(node -> node.name.endsWith("A"))
                .collect(Collectors.toSet());
        for (Node travelNode : travelNodes) {
            long step = 0L;
            String position = travelNode.getName();
            while (!position.endsWith("Z")) {
                String finalCurrentName = position;
                Node currentNode = nodes.stream()
                        .filter(node -> node.getName().equals(finalCurrentName))
                        .findFirst()
                        .orElseThrow();
                position = currentNode.getCorrectDestination(instructions.get((int) (step % instructions.size())));
                step++;
            }
            travelNode.steps = step;
        }

        List<Long> steps = travelNodes.stream()
                .map(Node::getSteps)
                .toList();

        long lcm = this.lcmAll(steps);
        System.out.println(lcm);
    }

    private Set<Node> extractNodes(List<String> fileContent) {
        Pattern pattern = Pattern.compile("(?<name>([A-Z]{3}))\\s+=\\s+\\((?<left>([A-Z]{3})), (?<right>([A-Z]{3}))\\)");

        Set<Node> nodes = new HashSet<>();
        for (String line : fileContent) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                nodes.add(new Node(matcher.group("name"), matcher.group("left"), matcher.group("right"), 0, new ArrayList<>()));
            }
        }
        return nodes;
    }

    // Calculate greatest common divisor
    private long gcd(long a, long b) {
        return (b == 0 ? a : gcd(b, a % b));
    }

    // Calculate least common multiple
    private long lcm(long a, long b) {
        return (a * b) / this.gcd(a, b);
    }

    // Calculate least common multiple for every steps
    private long lcmAll(List<Long> steps) {
        return steps.stream().reduce(this::lcm).orElseThrow();
    }
}
