package aoc.year2022.day08;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class TreetopTreeHouse {

    /**
     * Exercise :
     * - Part 1: count trees visible from outside grid
     * - Part 2: calculate ideal spot for tree house (tree with the highest scenic score)
     *
     * @param fileContent Content of given file
     */
    public void countTreesVisibleFromOutsideGrid(List<String> fileContent) {
        List<Tree> forest = parseForest(fileContent);

        int visibleTrees = (int) forest.stream()
                .filter(tree -> tree.isVisible(forest))
                .count();

        log.info("{} trees are visible from outside the grid.", visibleTrees);

        int highestScenicScore = forest.stream()
                .map(tree -> tree.scenicScore(forest))
                .max(Integer::compareTo)
                .orElse(0);

        log.info("{} is the highest scenic score possible for any tree.", highestScenicScore);
    }

    /**
     * Convert given file content to a forest
     *
     * @param fileContent Content of given file
     * @return Content as forest
     */
    private List<Tree> parseForest(List<String> fileContent) {
        List<Tree> forest = new ArrayList<>();

        for (int y = 0; y < fileContent.size(); y++) {
            List<Integer> treesHeight = Stream.of(fileContent.get(y).split(""))
                    .map(Integer::parseInt)
                    .toList();
            for (int x = 0; x < treesHeight.size(); x++) {
                forest.add(
                    new Tree(treesHeight.get(x), new Position(x, y))
                );
            }
        }

        return forest;
    }
}
