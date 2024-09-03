package aoc.year2018.day08;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@Slf4j
public class MemoryManeuver {
    private int memoryIndex;

    public void sumAllMetadatas(List<String> fileContent) {
        List<Integer> memoryData = this.extractNumbers(fileContent.get(0));

        this.memoryIndex = 0;
        Node rootNode = this.initNode(memoryData);

        int metadataSum = rootNode.metadataSum();
        log.info("Sum of all metadata entries : {}", metadataSum);

        int rootNodeValue = rootNode.calculateValue();
        log.info("Root node value : {}", rootNodeValue);
    }

    private List<Integer> extractNumbers(String data) {
        Pattern numberPattern = Pattern.compile("\\d+");
        return numberPattern.matcher(data).results()
                .map(MatchResult::group)
                .map(Integer::parseInt)
                .toList();
    }

    private Node initNode(List<Integer> memoryData) {
        int nbChildren = memoryData.get(memoryIndex);
        int nbMetadatas = memoryData.get(++memoryIndex);

        Node node = new Node(new ArrayList<>(), new ArrayList<>());

        // If node has children : init children
        IntStream.range(0, nbChildren)
                .forEach(ignored -> {
                    memoryIndex++;
                    Node child = initNode(memoryData);
                    node.getChildren().add(child);
                });

        // Then init metadata
        IntStream.range(0, nbMetadatas)
                .forEach(ignored -> node.getMetadatas().add(memoryData.get(++memoryIndex)));

        return node;
    }
}