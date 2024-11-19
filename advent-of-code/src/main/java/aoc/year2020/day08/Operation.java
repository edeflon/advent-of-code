package aoc.year2020.day08;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Operation {
    private OperationType type;
    private int value;
}
