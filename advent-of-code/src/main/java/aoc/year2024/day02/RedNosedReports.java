package aoc.year2024.day02;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class RedNosedReports {

    public void reportsAreSafe(List<String> fileContent) {
        List<Report> reports = this.parseReports(fileContent);

        int numberOfSafeReports = (int) reports.stream()
                .filter(this::isReportSafe)
                .count();
        log.info("{} reports are safe.", numberOfSafeReports);

        int numberOfSafeReportsWithProblemDampener = (int) reports.stream()
                .filter(report -> !this.isReportSafe(report))
                .map(report -> this.generateProblemDampenerReports(report).stream()
                        .anyMatch(this::isReportSafe))
                .filter(isSafe -> isSafe)
                .count();

        int totalOfSafeReports = numberOfSafeReports + numberOfSafeReportsWithProblemDampener;
        log.info("After applying the Problem Dampener, {} reports are safe.", totalOfSafeReports);
    }

    private List<Report> parseReports(List<String> fileContent) {
        List<Report> reports = new ArrayList<>();

        for (String line: fileContent) {
            List<Integer> levels = Stream.of(line.split("\\s+"))
                    .map(Integer::parseInt)
                    .toList();
            reports.add(new Report(levels));
        }

        return reports;
    }

    private boolean isReportSafe(Report report) {
        int difference;
        boolean increasing = false;
        boolean decreasing = false;

        for (int i = 0; i < (report.levels().size() - 1); i++) {
            difference = report.levels().get(i + 1) - report.levels().get(i);

            if (difference >= 0) {
                increasing = true;
            } else {
                decreasing = true;
            }

            if (increasing && decreasing || difference == 0 || Math.abs(difference) > 3) {
                return false;
            }
        }

        return true;
    }

    private List<Report> generateProblemDampenerReports(Report unsafeReport) {
        List<Report> problemDampenerReports = new ArrayList<>();

        for (int i = 0; i < unsafeReport.levels().size(); i++) {
            List<Integer> levelsCopy = new ArrayList<>(List.copyOf(unsafeReport.levels()));
            levelsCopy.remove(i);
            problemDampenerReports.add(new Report(levelsCopy));
        }

        return problemDampenerReports;
    }
}
