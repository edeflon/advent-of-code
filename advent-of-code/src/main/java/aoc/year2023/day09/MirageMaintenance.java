package aoc.year2023.day09;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MirageMaintenance {

    public void sumOfExtrapolatedValues(List<String> fileContent, boolean isSecondPart) {
        List<List<Long>> values = this.extractValues(fileContent);

        long sum = 0;
        for (List<Long> value : values) {
            List<List<Long>> sequences = new ArrayList<>();
            sequences.add(value);

            boolean cycleComplete = false;
            int step = 0;
            while (!cycleComplete) {
                List<Long> sequence = this.calculateSequence(sequences.get(step));
                sequences.add(sequence);
                cycleComplete = sequence.stream().allMatch(number -> number == 0L);
                step++;
            }

            long history;
            if (isSecondPart) {
                history = this.calculateBackwardHistory(sequences);
            } else {
                history = this.calculateHistory(sequences);
            }
            sum += history;
        }

        System.out.println(sum);
    }

    private List<List<Long>> extractValues(List<String> fileContent) {
        List<List<Long>> values = new ArrayList<>();

        for (String line: fileContent) {
            Pattern pattern = Pattern.compile("(-)?(\\d+)");
            Matcher matcher = pattern.matcher(line);

            List<Long> history = new ArrayList<>();
            while (matcher.find()) {
                history.add(Long.valueOf(matcher.group()));
            }
            values.add(history);
        }

        return values;
    }

    private List<Long> calculateSequence(List<Long> previousSequence) {
        List<Long> sequence = new ArrayList<>();
        for (int j = 0; j < previousSequence.size() - 1; j++) {
            sequence.add(previousSequence.get(j + 1) - previousSequence.get(j));
        }
        return sequence;
    }

    private Long calculateHistory(List<List<Long>> sequences) {
        long history = 0;
        for (int i = sequences.size() - 2; i >= 0; i--) {
            history += sequences.get(i).get(sequences.get(i).size() - 1);
        }
        return history;
    }

    private Long calculateBackwardHistory(List<List<Long>> sequences) {
        long backwardHistory = 0;
        for (int i = sequences.size() - 2; i >= 0; i--) {
            backwardHistory = sequences.get(i).get(0) - backwardHistory;
        }
        return backwardHistory;
    }
}
