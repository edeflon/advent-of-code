package aoc.year2021.day06;

import lombok.Getter;

import java.util.Optional;

@Getter
public class Lanternfish {
    private int reproductiveTimer;

    Lanternfish() {
        this.reproductiveTimer = 8;
    }

    Lanternfish(int reproductiveTimer) {
        this.reproductiveTimer = reproductiveTimer;
    }

    public Optional<Lanternfish> aDayPassed() {
        if (0 == this.reproductiveTimer) {
            this.reproductiveTimer = 6;
            return Optional.of(this.reproduce());
        } else {
            this.reproductiveTimer--;
            return Optional.empty();
        }
    }

    private Lanternfish reproduce() {
        return new Lanternfish();
    }
}
