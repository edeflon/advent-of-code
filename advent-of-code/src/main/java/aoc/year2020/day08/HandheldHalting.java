package aoc.year2020.day08;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class HandheldHalting {

    public void accumulatorCalculations(List<String> fileContent) {
        List<Operation> operations = extractOperation(fileContent);

        // PART 1
        int acc = this.accumulator(operations);
        log.info("The value in the accumulator is {}.", acc);

        // PART 2
        List<Operation> reparedOperations = new ArrayList<>(operations);
        for (Operation operation: reparedOperations) {
            if (!operation.getType().equals(OperationType.ACC)) {
                OperationType initialType = operation.getType();
                if (initialType.equals(OperationType.JMP)) {
                    operation.setType(OperationType.NOP);
                } else {
                    operation.setType(OperationType.JMP);
                }

                Optional<Integer> optionalAcc = this.completeAccumulator(reparedOperations);

                if (optionalAcc.isPresent()) {
                    acc = optionalAcc.get();
                    break;
                } else {
                    operation.setType(initialType);
                }
            }
        }

        log.info("The value in the accumulator after the program terminates is {}.", acc);
    }

    private List<Operation> extractOperation(List<String> fileContent) {
        List<Operation> operations = new ArrayList<>();
        for (String content : fileContent) {
            Pattern pattern = Pattern.compile("(?<type>\\w+) (?<value>(-|\\+)\\w+)");
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                OperationType type = OperationType.valueOf(matcher.group("type").toUpperCase());
                int value = Integer.parseInt(matcher.group("value"));
                operations.add(new Operation(type, value));
            }
        }
        return operations;
    }

    private int accumulator(List<Operation> operations) {
        int index = 0;
        int acc = 0;
        Set<Integer> executedIndex = new HashSet<>();

        do {
            Operation currentOperation = operations.get(index);
            switch(currentOperation.getType()) {
                case ACC :
                    acc += currentOperation.getValue();
                    index++;
                    break;
                case JMP :
                    index += currentOperation.getValue();
                    break;
                default:
                    index++;
                    break;
            }
        } while (executedIndex.add(index));

        return acc;
    }

    private Optional<Integer> completeAccumulator(List<Operation> operations) {
        int index = 0;
        int acc = 0;
        Set<Integer> executedIndex = new HashSet<>();

        do {
            Operation currentOperation = operations.get(index);
            switch(currentOperation.getType()) {
                case ACC :
                    acc += currentOperation.getValue();
                    index++;
                    break;
                case JMP :
                    index += currentOperation.getValue();
                    break;
                default:
                    index++;
                    break;
            }
        } while (index < operations.size() && executedIndex.add(index));

        if (index >= operations.size() && !executedIndex.contains(index)) {
            return Optional.of(acc);
        }

        return Optional.empty();
    }
}
