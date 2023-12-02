package aoc.year2023.day02;

import lombok.Data;

@Data
public class GameSet {
    private int redCubes;
    private int greenCubes;
    private int blueCubes;

    public void increaseMatchingCubes(int number, CubeType type) {
        switch (type) {
            case RED -> redCubes += number;
            case GREEN -> greenCubes += number;
            case BLUE -> blueCubes += number;
        }
    }

    public boolean isPossibleWithBagContent(BagContent bagContent) {
        return (
                bagContent.redCubes() >= this.getRedCubes()
                        && bagContent.greenCubes() >= this.getGreenCubes()
                        && bagContent.blueCubes() >= this.getBlueCubes()
        );
    }
}
