package aoc.year2022.day08;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Data
public class Tree {
    private int height;
    private Position position;

    /**
     * Verify if tree is visible from outside of given forest
     *
     * @param forest Trees of the forest
     * @return Verify if current tree is visible from outside of given forest
     */
    public boolean isVisible(List<Tree> forest) {
        return 0 == this.countHigherTreesOnTop(forest)
                || 0 == this.countHigherTreesOnLeft(forest)
                || 0 == this.countHigherTreesOnRight(forest)
                || 0 == this.countHigherTreesOnBottom(forest);
    }

    /**
     * Count number of trees higher than current tree from the top of the forest
     *
     * @param forest Trees of the forest
     * @return Number of tree higher than current tree
     */
    private int countHigherTreesOnTop(List<Tree> forest) {
        return (int) forest.stream()
                .filter(tree -> tree.getHeight() >= this.getHeight()
                        && tree.getPosition().getX() == this.getPosition().getX()
                        && tree.getPosition().getY() < this.getPosition().getY())
                .count();
    }

    /**
     * Count number of trees higher than current tree from the left of the forest
     *
     * @param forest Trees of the forest
     * @return Number of tree higher than current tree
     */
    private int countHigherTreesOnLeft(List<Tree> forest) {
        return (int) forest.stream()
                .filter(tree -> tree.getHeight() >= this.getHeight()
                        && tree.getPosition().getX() > this.getPosition().getX()
                        && tree.getPosition().getY() == this.getPosition().getY())
                .count();
    }

    /**
     * Count number of trees higher than current tree from the right of the forest
     *
     * @param forest Trees of the forest
     * @return Number of tree higher than current tree
     */
    private int countHigherTreesOnRight(List<Tree> forest) {
        return (int) forest.stream()
                .filter(tree -> tree.getHeight() >= this.getHeight()
                        && tree.getPosition().getX() < this.getPosition().getX()
                        && tree.getPosition().getY() == this.getPosition().getY())
                .count();
    }

    /**
     * Count number of trees higher than current tree from the bottom of the forest
     *
     * @param forest Trees of the forest
     * @return Number of tree higher than current tree
     */
    private int countHigherTreesOnBottom(List<Tree> forest) {
        return (int) forest.stream()
                .filter(tree -> tree.getHeight() >= this.getHeight()
                        && tree.getPosition().getX() == this.getPosition().getX()
                        && tree.getPosition().getY() > this.getPosition().getY())
                .count();
    }

    /**
     * Calculate scenic score of current tree
     *
     * @param forest Trees of the forest
     * @return Number of trees visible from current tree
     */
    public int scenicScore(List<Tree> forest) {
        return this.topScenicScore(forest) * this.leftScenicScore(forest) * this.rightScenicScore(forest) * this.bottomScenicScore(forest);
    }

    /**
     * Calculate top scenic score
     *
     * @param forest Trees of the forest
     * @return Number of trees visible from current tree on the top of the forest
     */
    private int topScenicScore(List<Tree> forest) {
        int scenicScore = 0;
        List<Tree> treesOnTop = forest.stream()
                .filter(tree -> tree.getPosition().getY() == this.getPosition().getY())
                .toList();

        if (treesOnTop.isEmpty()) {
            return scenicScore;
        }

        Optional<Tree> optionalTree = treesOnTop.stream()
                        .filter(tree -> tree.getPosition().getX() == this.getPosition().getX() - 1)
                        .findFirst();

        while(optionalTree.isPresent() && optionalTree.get().getHeight() < this.getHeight()) {
            int previousX = optionalTree.get().getPosition().getX();

            optionalTree = treesOnTop.stream()
                    .filter(tree -> tree.getPosition().getX() == previousX - 1)
                    .findFirst();

            scenicScore++;
        }

        // Count last tree
        if (optionalTree.isPresent()) {
            scenicScore++;
        }

        return scenicScore;
    }

    /**
     * Calculate left scenic score
     *
     * @param forest Trees of the forest
     * @return Number of trees visible from current tree on the left of the forest
     */
    private int leftScenicScore(List<Tree> forest) {
        int scenicScore = 0;
        List<Tree> treesOnLeft = forest.stream()
                .filter(tree -> tree.getPosition().getX() == this.getPosition().getX())
                .toList();

        if (treesOnLeft.isEmpty()) {
            return scenicScore;
        }

        Optional<Tree> optionalTree = treesOnLeft.stream()
                .filter(tree -> tree.getPosition().getY() == this.getPosition().getY() - 1)
                .findFirst();

        while(optionalTree.isPresent() && optionalTree.get().getHeight() < this.getHeight()) {
            int previousY = optionalTree.get().getPosition().getY();

            optionalTree = treesOnLeft.stream()
                    .filter(tree -> tree.getPosition().getY() == previousY - 1)
                    .findFirst();

            scenicScore++;
        }

        // Count last tree
        if (optionalTree.isPresent()) {
            scenicScore++;
        }

        return scenicScore;
    }

    /**
     * Calculate right scenic score
     *
     * @param forest Trees of the forest
     * @return Number of trees visible from current tree on the right of the forest
     */
    private int rightScenicScore(List<Tree> forest) {
        int scenicScore = 0;
        List<Tree> treesOnRight = forest.stream()
                .filter(tree -> tree.getPosition().getX() == this.getPosition().getX())
                .toList();

        if (treesOnRight.isEmpty()) {
            return scenicScore;
        }

        Optional<Tree> optionalTree = treesOnRight.stream()
                .filter(tree -> tree.getPosition().getY() == this.getPosition().getY() + 1)
                .findFirst();

        while (optionalTree.isPresent() && optionalTree.get().getHeight() < this.getHeight()) {
            int previousY = optionalTree.get().getPosition().getY();

            optionalTree = treesOnRight.stream()
                    .filter(tree -> tree.getPosition().getY() == previousY + 1)
                    .findFirst();

            scenicScore++;
        }

        // Count last tree
        if (optionalTree.isPresent()) {
            scenicScore++;
        }

        return scenicScore;
    }

    /**
     * Calculate bottom scenic score
     *
     * @param forest Trees of the forest
     * @return Number of trees visible from current tree on the bottom of the forest
     */
    private int bottomScenicScore(List<Tree> forest) {
        int scenicScore = 0;
        List<Tree> treesOnBottom = forest.stream()
                .filter(tree -> tree.getPosition().getY() == this.getPosition().getY())
                .toList();

        if (treesOnBottom.isEmpty()) {
            return scenicScore;
        }

        Optional<Tree> optionalTree = treesOnBottom.stream()
                .filter(tree -> tree.getPosition().getX() == this.getPosition().getX() + 1)
                .findFirst();

        while(optionalTree.isPresent() && optionalTree.get().getHeight() < this.getHeight()) {
            int previousX = optionalTree.get().getPosition().getX();

            optionalTree = treesOnBottom.stream()
                    .filter(tree -> tree.getPosition().getX() == previousX + 1)
                    .findFirst();

            scenicScore++;
        }

        // Count last tree
        if (optionalTree.isPresent()) {
            scenicScore++;
        }

        return scenicScore;
    }
}
