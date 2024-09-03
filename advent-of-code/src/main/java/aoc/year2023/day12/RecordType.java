package aoc.year2023.day12;

import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
public enum RecordType {
    OPERATIONAL("."),
    DAMAGED("#"),
    UNKNOWN("?");

    private final String type;

    RecordType(String type) {
        this.type = type;
    }

    public static RecordType getRecordType(String type) {
        return Stream.of(RecordType.values())
                .filter(recordType -> Objects.equals(recordType.getType(), type))
                .findFirst()
                .orElseThrow();
    }
}
