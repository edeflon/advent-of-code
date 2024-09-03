package aoc.year2023.day12;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class HotSprings {

    public void sumOfCounts(List<String> fileContent) {
        Set<ConditionRecord> records = this.extractConditionRecords(fileContent);

        // Count all possibilities for each records
        long possibilities = records.stream()
                .map(ConditionRecord::countPossibilities)
                .reduce(0L, Long::sum);

        // Display results
        System.out.println(possibilities);
    }

    private Set<ConditionRecord> extractConditionRecords(List<String> fileContent) {
        Pattern pattern = Pattern.compile("(?<record>(#|\\?|\\.)+)\\s+(?<duplicate>(([0-9]|,)+))");

        Set<ConditionRecord> records = new HashSet<>();

        for (String line : fileContent) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                List<RecordType> conditionRecords = Stream.of(matcher.group("record").split(""))
                        .map(RecordType::getRecordType)
                        .toList();
                List<Integer> duplicatedInformations = Stream.of(matcher.group("duplicate").split(","))
                        .map(Integer::parseInt)
                        .toList();
                records.add(
                        new ConditionRecord(
                                conditionRecords,
                                duplicatedInformations
                        )
                );
            }
        }

        return records;
    }
}
