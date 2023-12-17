package aoc.year2023.day14;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class Rock {
    private Position position;
    private RockType type;

    public Rock moveNorth(int index, List<Rock> rocks) {
        if (this.getType() == RockType.CUBE) {
            return this;
        }

        Optional<Integer> closestY = rocks.stream()
                .filter(rock -> rock.getPosition().y() < this.getPosition().y() && rock.getPosition().x() == this.getPosition().x())
                .map(rock -> rock.getPosition().y())
                .max(Integer::compareTo);

        Rock newRock = new Rock(
                new Position(
                        this.position.x(),
                        closestY.map(integer -> integer + 1).orElse(0)
                ),
                this.getType()
        );
        rocks.set(index, newRock);

        return newRock;
    }

    public Rock moveWest(int index, List<Rock> rocks) {
        if (this.getType() == RockType.CUBE) {
            return this;
        }

        Optional<Integer> closestX = rocks.stream()
                .filter(rock -> rock.getPosition().x() < this.getPosition().x() && rock.getPosition().y() == this.getPosition().y())
                .map(rock -> rock.getPosition().x())
                .max(Integer::compareTo);

        Rock newRock = new Rock(
                new Position(
                        closestX.map(integer -> integer + 1).orElse(0),
                        this.position.y()
                ),
                this.getType()
        );
        rocks.set(index, newRock);

        return newRock;
    }

    public Rock moveEast(int index, List<Rock> rocks) {
        if (this.getType() == RockType.CUBE) {
            return this;
        }

        Optional<Integer> closestX = rocks.stream()
                .filter(rock -> rock.getPosition().x() > this.getPosition().x() && rock.getPosition().y() == this.getPosition().y())
                .map(rock -> rock.getPosition().x())
                .min(Integer::compareTo);

        Rock newRock = new Rock(
                new Position(
                        closestX.map(integer -> integer - 1).orElse(
                                rocks.stream()
                                        .map(rock -> rock.getPosition().x())
                                        .max(Integer::compareTo)
                                        .orElse(0)
                        ),
                        this.position.y()
                ),
                this.getType()
        );
        rocks.set(index, newRock);

        return newRock;
    }

    public Rock moveSouth(int index, List<Rock> rocks) {
        if (this.getType() == RockType.CUBE) {
            return this;
        }

        Optional<Integer> closestY = rocks.stream()
                .filter(rock -> rock.getPosition().y() > this.getPosition().y() && rock.getPosition().x() == this.getPosition().x())
                .map(rock -> rock.getPosition().y())
                .min(Integer::compareTo);

        Rock newRock = new Rock(
                new Position(
                        this.position.x(),
                        closestY.map(integer -> integer - 1).orElse(
                                rocks.stream()
                                        .map(rock -> rock.getPosition().y())
                                        .max(Integer::compareTo)
                                        .orElse(0)
                        )
                ),
                this.getType()
        );
        rocks.set(index, newRock);

        return newRock;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Rock rock)) {
            return false;
        }

        return (this.getPosition().x() == rock.getPosition().x() && this.getPosition().y() == rock.getPosition().y() && this.getType() == rock.getType());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
