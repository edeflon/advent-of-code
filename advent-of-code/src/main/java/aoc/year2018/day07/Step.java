package aoc.year2018.day07;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
@AllArgsConstructor
public class Step implements Comparable<Step> {
    private String id;
    private List<String> previousIds;
    private Status status;

    public boolean isAllPreviousCompleted(Set<Step> steps) {
        for (String prevId: previousIds) {
            Optional<Step> step = steps.stream()
                    .filter(s -> s.getId().equals(prevId))
                    .findFirst();
            if (step.isPresent() && step.get().getStatus().equals(Status.READY)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(@NotNull Step step) {
        return this.getId().compareTo(step.getId());
    }
}
