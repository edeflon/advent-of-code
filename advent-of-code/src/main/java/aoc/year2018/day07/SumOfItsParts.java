package aoc.year2018.day07;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SumOfItsParts {

    public void stepOrderExecution(List<String> fileContent) {
        // Get all steps from file content
        Set<Step> steps = this.extractSteps(fileContent);

        boolean completed = false;
        StringBuilder order = new StringBuilder();

        while (!completed) {
            // Find next available step
            Optional<Step> optionalStep = steps.stream()
                    .filter(Step::isAvailable)
                    .sorted()
                    .findFirst();

            // If a step is available : complete it and add its id to the final order, else we're done
            if (optionalStep.isPresent()) {
                Step nextStep = optionalStep.get();
                nextStep.setCompleted(true);
                order.append(nextStep.getId());
            } else {
                completed = true;
            }
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

                // Treat previous step first
                Optional<Step> optionalPreviousStep = steps.stream()
                        .filter(step -> step.getId().equals(previousId))
                        .findFirst();

                // If previous step does not exist : create it and add it to step set
                Step previousStep;
                if (optionalPreviousStep.isEmpty()) {
                    previousStep = new Step(previousId, new ArrayList<>(), false);
                    steps.add(previousStep);
                } else {
                    previousStep = optionalPreviousStep.get();
                }

                // Verify if current step exist
                steps.stream()
                        .filter(step -> step.getId().equals(id))
                        .findFirst()
                        .ifPresentOrElse(
                                // If it's present, we had previous step to its previous steps list
                                step -> step.getPreviousSteps().add(previousStep),
                                // If not, we create it, with only previous step in its previous steps list
                                () -> steps.add(new Step(id, new ArrayList<>(List.of(previousStep)), false))
                        );
            }
        }

        return steps;
    }
}
