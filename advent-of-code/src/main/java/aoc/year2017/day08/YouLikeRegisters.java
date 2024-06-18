package aoc.year2017.day08;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class YouLikeRegisters {
    public void largestValueInAnyRegister(List<String> fileContent) {
        List<RegisterCalculation> registerCalculations = extractRegisters(fileContent);

        Map<String, Register> registersPerName = registerCalculations.stream()
                .map(reg -> List.of(reg.getName(), reg.getCondition().registerName()))
                .flatMap(List::stream)
                .distinct()
                .map(reg -> new Register(reg, 0))
                .collect(Collectors.toMap(Register::getName, Function.identity()));

        int highestValue = Integer.MIN_VALUE;
        for (RegisterCalculation registerCalculation : registerCalculations) {
            Register register = registersPerName.get(registerCalculation.getName());
            Register conditionReg = registersPerName.get(registerCalculation.getCondition().registerName());

            if (isConditionVerified(conditionReg, registerCalculation.getCondition().value(), registerCalculation.getCondition().comparator())) {
                String operation = registerCalculation.getInstruction().operation();
                if ("inc".equals(operation)) {
                    register.setValue(register.getValue() + registerCalculation.getInstruction().value());
                } else {
                    register.setValue(register.getValue() - registerCalculation.getInstruction().value());
                }
            }

            if (highestValue < register.getValue()) {
                highestValue = register.getValue();
            }

            registersPerName.replace(register.getName(), register);
        }

        int largestValue = registersPerName.values().stream()
                .map(Register::getValue)
                .max(Integer::compareTo)
                .orElseThrow();

        log.info("Part One : {}", largestValue);
        log.info("Part Two : {}", highestValue);
    }

    private boolean isConditionVerified(Register register, int value, String comparator) {
        return switch (comparator) {
            case "==" -> register.getValue() == value;
            case "!=" -> register.getValue() != value;
            case ">" -> register.getValue() > value;
            case ">=" -> register.getValue() >= value;
            case "<" -> register.getValue() < value;
            case "<=" -> register.getValue() <= value;
            default -> {
                log.warn("Comparator not handled: {}", comparator);
                yield false;
            }
        };
    }

    private List<RegisterCalculation> extractRegisters(List<String> fileContent) {
        List<RegisterCalculation> registerCalculations = new ArrayList<>();
        for (String content : fileContent) {
            Pattern pattern = Pattern.compile("(?<mainRegister>\\w+) (?<operation>inc|dec) (?<operationValue>-?\\d+) if (?<otherRegister>\\w+) (?<comparator>[!=<>]+) (?<comparisonValue>-?\\d+)");
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                Instruction instruction = new Instruction(matcher.group("operation"), Integer.parseInt(matcher.group("operationValue")));
                Condition condition = new Condition(matcher.group("otherRegister"), matcher.group("comparator"), Integer.parseInt(matcher.group("comparisonValue")));
                RegisterCalculation registerCalculation = new RegisterCalculation(matcher.group("mainRegister"), instruction, condition);
                registerCalculations.add(registerCalculation);
            }
        }
        return registerCalculations;
    }
}
