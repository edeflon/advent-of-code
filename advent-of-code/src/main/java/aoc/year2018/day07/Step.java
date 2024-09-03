package aoc.year2018.day07;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@AllArgsConstructor
public class Step implements Comparable<Step> {
    private String id;
    private List<Step> previousSteps;
    private boolean completed;

    /**
     * Verify if step is available to run
     *
     * @return Status if step is available to run
     */
    public boolean isAvailable() {
        return (previousSteps.isEmpty() || isAllPreviousCompleted()) && !completed;
    }

    /**
     * Verify if all previous steps are completed
     *
     * @return Status if all previous steps are completed
     */
    public boolean isAllPreviousCompleted() {
        return previousSteps.stream()
                .filter(prevStep -> !prevStep.isCompleted())
                .toList()
                .isEmpty();
    }

    @Override
    public int compareTo(@NotNull Step step) {
        return this.getId().compareTo(step.getId());
    }
}
