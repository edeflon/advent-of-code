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

        // Find damaged groups and unknowns size and index
        List<RecordGroup> damagedGroups = this.findRecordGroups(RecordType.DAMAGED);
//        List<RecordGroup> unknownGroups = this.findRecordGroups(RecordType.UNKNOWN);

        // Reconstruct matching damaged groups with duplicatedInformations
        List<Integer> checkedInformations = this.findInformationsAvailableInGroups(damagedGroups);

        // Count remaining possible positions in
        return 0L;
    }

    private List<RecordGroup> findRecordGroups(RecordType type) {
        List<RecordGroup> recordGroups = new ArrayList<>();

        int count = 0;
        RecordGroup recordGroup = new RecordGroup();
        for (int i = 0; i < this.conditionRecords.size(); i++) {
            if (this.conditionRecords.get(i) == type) {
                count++;
                if (recordGroup.getStartIndex() == null) {
                    recordGroup.setStartIndex(i);
                }
            } else if (count > 0) {
                recordGroup.setType(type);
                recordGroup.setSize(count);
                recordGroups.add(recordGroup);
                recordGroup = new RecordGroup();
                count = 0;
            }
        }

        if (count > 0) {
            recordGroup.setType(type);
            recordGroup.setSize(count);
            recordGroups.add(recordGroup);
        }

        return recordGroups;
    }

    private List<Integer> findInformationsAvailableInGroups(List<RecordGroup> damagedGroups, List<RecordGroup> unknownGroups) {
        for (RecordGroup group : damagedGroups) {
            // Check if possible to match a duplicateInfo

            // If yes -> mark duplicateInfo as checked
            // TODO : check if possible to put

            // If no -> try to complete with unknown
        }
    }
}
