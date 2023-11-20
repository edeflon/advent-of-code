package aoc.year2021.day06;

import lombok.Getter;

@Getter
public class Lanternfish {
    private int reproductiveTimer;

    Lanternfish() {
        this.reproductiveTimer = 8;
    }

    Lanternfish(int reproductiveTimer) {
        this.reproductiveTimer = reproductiveTimer;
    }

    public void aDayPass() {
        if (0 == this.reproductiveTimer) {
            this.reproductiveTimer = 6;
        } else {
            this.reproductiveTimer -= 1;
        }
    }

    public Lanternfish reproduce() {
        return new Lanternfish();
    }
}
