package aoc.year2023.day02;

import lombok.Data;

import java.util.List;

@Data
public class Game {
    private int id;
    private List<GameSet> gameSets;

    public boolean isPossibleWithBagContent(BagContent bagContent) {
        for (GameSet gameSet: gameSets) {
            if (!gameSet.isPossibleWithBagContent(bagContent)) {
                return false;
            }
        }
        return true;
    }

    public int power() {
        int maxRed = 0;
        int maxGreen = 0;
        int maxBlue = 0;

        for (GameSet gameSet: gameSets) {
            maxRed = Math.max(gameSet.getRedCubes(), maxRed);
            maxGreen = Math.max(gameSet.getGreenCubes(), maxGreen);
            maxBlue = Math.max(gameSet.getBlueCubes(), maxBlue);
        }

        return maxRed * maxGreen * maxBlue;
    }
}
