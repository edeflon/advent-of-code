package aoc.year2018.day07;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class SumOfItsParts {

    public void stepOrderExecution(List<String> fileContent) {
        // Get all steps from file content
        Set<Step> steps = this.extractSteps(fileContent);

        // Find all available steps to begin treatment
        List<Step> availableSteps = new ArrayList<>(
                this.findFirstsSteps(steps)
                        .stream()
                        .map(stepId -> new Step(stepId, new ArrayList<>(), Status.READY))
                        .toList()
        );

        // Select the first one to do (by alphabetical order) and set its status to completed
        Step first = availableSteps.stream()
                .sorted()
                .findFirst()
                .orElseThrow();
        first.setStatus(Status.COMPLETED);
        steps.addAll(availableSteps);

        String id = first.getId();
        boolean completed = false;
        StringBuilder order = new StringBuilder(id);

        // While all the steps are not completed
        while (!completed) {
            String finalId = id;

            // Remove completed steps from available steps
            availableSteps.removeAll(
                    steps.stream()
                            .filter(step -> step.getStatus().equals(Status.COMPLETED))
                            .toList()
            );

            // Add all steps available after previous step was completed
            availableSteps.addAll(
                    steps.stream()
                            .filter(step -> step.getPreviousIds().contains(finalId) && step.getStatus().equals(Status.READY))
                            .toList()
            );

            // Select the next step to complete (by alphabetical order) and complete it
            Step nextStep = availableSteps.stream()
                    .filter(step -> step.isAllPreviousCompleted(steps))
                    .sorted()
                    .findFirst()
                    .orElseThrow();
            nextStep.setStatus(Status.COMPLETED);

            id = nextStep.getId();
            order.append(id);

            // Verify if all the steps are completed
            completed = steps.stream()
                    .map(Step::getStatus)
                    .filter(status -> status == Status.READY)
                    .collect(Collectors.toSet())
                    .isEmpty();
        }

        log.info("The steps in your instructions should be completed in this order : {}", order);
    }

    /**
     * Extract steps from file content
     *
     * @param fileContent Lines from file corresponding to exercice
     * @return Set of steps
     */
    private Set<Step> extractSteps(List<String> fileContent) {
        Set<Step> steps = new HashSet<>();

        for (String line : fileContent) {
            Pattern pattern = Pattern.compile("Step (?<previousId>\\w) must be finished before step (?<id>\\w) can begin.");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String id = matcher.group("id");
                String previousId = matcher.group("previousId");
                Optional<Step> optionalStep = steps.stream()
                        .filter(step -> step.getId().equals(id))
                        .findFirst();

                optionalStep.ifPresentOrElse(
                        step -> step.getPreviousIds().add(previousId),
                        () -> steps.add(new Step(id, new ArrayList<>(List.of(previousId)), Status.READY))
                );
            }
        }

        return steps;
    }

    /**
     * Find available steps in given step set
     *
     * @param steps Set of sets
     * @return List of step ids available to run
     */
    private List<String> findFirstsSteps(Set<Step> steps) {
        Set<String> allPreviousIds = steps.stream()
                .map(Step::getPreviousIds)
                .flatMap(List::stream)
                .collect(Collectors.toSet());

        List<String> firstsSteps = new ArrayList<>();
        for (String previousId : allPreviousIds) {
            Optional<Step> optionalFirstStep = steps.stream()
                    .filter(step -> step.getId().equals(previousId))
                    .findFirst();
            if (optionalFirstStep.isEmpty()) {
                firstsSteps.add(previousId);
            }
        }

        return firstsSteps;
    }
}
