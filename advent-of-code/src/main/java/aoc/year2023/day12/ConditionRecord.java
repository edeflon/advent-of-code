package aoc.year2023.day12;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class ConditionRecord {
    List<RecordType> conditionRecords;
    List<Integer> duplicatedInformations;

    public long countPossibilities() {
        List<List<RecordType>> possibilities = new ArrayList<>();

        List<RecordType> possibility;
        do {
            possibility = this.buildNewPossibility(possibilities);
            if (!possibility.isEmpty()) {
                possibilities.add(possibility);
            }
        } while (!possibility.isEmpty());

        return possibilities.size();
    }

    private List<RecordType> buildNewPossibility(List<List<RecordType>> possibilities) {
        List<RecordType> possibility = new ArrayList<>(this.conditionRecords);

        int duplicateIndex = 0;
        int damagedCount = 0;
        for (int i = 0; i < possibility.size(); i++) {
            RecordType recordType = possibility.get(i);
            switch (recordType) {
                case DAMAGED -> damagedCount++;
                case OPERATIONAL -> {
                    if (damagedCount == this.duplicatedInformations.get(duplicateIndex)) {
                        damagedCount = 0;
                        duplicateIndex++;
                    } else {
                        possibility.set(i - damagedCount, RecordType.OPERATIONAL);
                        damagedCount--;
                    }
                }
                case UNKNOWN -> {
                    if (damagedCount == this.duplicatedInformations.get(duplicateIndex)) {
                        damagedCount = 0;
                        possibility.set(i, RecordType.OPERATIONAL);
                        duplicateIndex++;
                    } else {
                        possibility.set(i, RecordType.DAMAGED);
                        damagedCount++;
                    }
                }
            }

            if (possibilities.contains(possibility)) {
                throw new IllegalArgumentException("AAAAAAAAAAAAAAAHHHHHHH");
            }

            if (duplicateIndex == this.duplicatedInformations.size()) {
                break;
            }
        }

        return possibility;
    }
}
