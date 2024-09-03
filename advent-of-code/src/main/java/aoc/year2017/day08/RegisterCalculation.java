package aoc.year2017.day08;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterCalculation {
    private String name;
    private Instruction instruction;
    private Condition condition;
}
