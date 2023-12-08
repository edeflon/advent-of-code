package aoc.year2023.day08;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        int steps = 0;
        while (!travelNodes.stream().allMatch(node -> node.name.endsWith("Z"))) {
            String instruction = instructions.get(steps % instructions.size());
            travelNodes = travelNodes.stream()
                    .map(node -> this.findNextNode(node, instruction, nodes))
                    .collect(Collectors.toSet());
            steps++;
        }

//        int steps = 0;
//        String target = "ZZZ";
//        String position = "AAA";
//        while (!target.equals(position)) {
//            String finalCurrentName = position;
//            Node currentNode = nodes.stream()
//                    .filter(node -> node.getName().equals(finalCurrentName))
//                    .findFirst()
//                    .orElseThrow();
//            position = currentNode.getCorrectDestination(instructions.get(steps % instructions.size()));
//            steps++;
//        }

        System.out.println(steps);
    }

    private Set<Node> extractNodes(List<String> fileContent) {
        Pattern pattern = Pattern.compile("(?<name>([A-Z]{3}))\\s+=\\s+\\((?<left>([A-Z]{3})), (?<right>([A-Z]{3}))\\)");

        Set<Node> nodes = new HashSet<>();
        for (String line: fileContent) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                nodes.add(new Node(matcher.group("name"), matcher.group("left"), matcher.group("right")));
            }
        }
        return nodes;
    }

    private Node findNextNode(Node currentNode, String instruction, Set<Node> nodes) {
        String next = currentNode.getCorrectDestination(instruction);
        return nodes.stream()
                .filter(nextNode -> nextNode.name.equals(next))
                .findFirst()
                .orElseThrow();
    }
}
