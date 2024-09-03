package aoc.year2023.day15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LensLibrary {

    public void sumOfResults(List<String> fileContent) {
        List<String> steps = List.of(fileContent.get(0).split(","));

        // P1
        int value = 0;
        for (String step : steps) {
            value += this.hash(step);
        }

        System.out.println(value);

        // P2
        Map<Integer, List<Step>> stepMap = new HashMap<>();

        for (String step : steps) {
            Optional<Step> optionalStep = this.extractSplitStep(step);
            optionalStep.ifPresent(splitStep -> this.updateStepMap(stepMap, splitStep));
        }

        int power = this.focusingPower(stepMap);

        System.out.println(power);
    }

    private int hash(String step) {
        int value = 0;
        for (int i = 0; i < step.length(); i++) {
            value = ((value + step.charAt(i)) * 17) % 256;
        }
        return value;
    }

    private int focusingPower(Map<Integer, List<Step>> stepMap) {
        int power = 0;
        for (Map.Entry<Integer, List<Step>> entry : stepMap.entrySet()) {
            int key = entry.getKey();
            List<Step> values = entry.getValue();
            for (Step step : values) {
                power += (key + 1) * (values.indexOf(step) + 1) * step.value();
            }
        }
        return power;
    }

    private Optional<Step> extractSplitStep(String step) {
        Pattern pattern = Pattern.compile("(?<sequence>[A-z]+)(?<sign>[=-])(?<value>\\d*)");
        Matcher matcher = pattern.matcher(step);
        if (matcher.find()) {
            String stepValue = matcher.group("value");
            int stepNumber = stepValue.isBlank() ? -1 : Integer.parseInt(stepValue);
            return Optional.of(new Step(
                    matcher.group("sequence"),
                    matcher.group("sign"),
                    stepNumber
            ));
        }
        return Optional.empty();
    }

    private void updateStepMap(Map<Integer, List<Step>> stepMap, Step splitStep) {
        int index = this.hash(splitStep.sequence());

        stepMap.computeIfAbsent(index, value -> new ArrayList<>());

        if (splitStep.sign().equals("-")) {
            List<Step> remainingSteps = stepMap.get(index).stream()
                    .filter(savedStep -> !Objects.equals(savedStep.sequence(), splitStep.sequence()))
                    .collect(Collectors.toCollection(ArrayList::new));
            stepMap.put(index, remainingSteps);
        } else {
            Optional<Step> presentStep = stepMap.get(index).stream()
                    .filter(savedStep -> Objects.equals(savedStep.sequence(), splitStep.sequence()))
                    .findFirst();
            if (presentStep.isEmpty()) {
                stepMap.get(index).add(splitStep);
            } else {
                int stepIndex = stepMap.get(index).indexOf(presentStep.get());
                stepMap.get(index).set(stepIndex, splitStep);
            }
        }
    }
}
