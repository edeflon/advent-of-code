package aoc.year2023.day16;

import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
public enum ContraptionElement {
    MIRROR_SLASH("/"),
    MIRROR_ANTI("\\"),
    SPLITTER_V("|"),
    SPLITTER_H("-");

    private final String character;

    ContraptionElement(String character) {
        this.character = character;
    }

    public static ContraptionElement getByCharacter(String character) {
        return Stream.of(ContraptionElement.values())
                .filter(element -> Objects.equals(element.character, character))
                .findFirst()
                .orElseThrow();
    }
}
