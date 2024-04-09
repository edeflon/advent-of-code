package aoc.year2016.day07;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class InternetProtocolVersion7 {

    static final Pattern IPV7_LEFT_PATTERN = Pattern.compile("(\\w+)\\[");
    static final Pattern IPV7_INSIDE_PATTERN = Pattern.compile("\\[(\\w+)\\]");
    static final Pattern IPV7_RIGHT_PATTERN = Pattern.compile("\\](\\w+)");

    public void countIpsSupportingTls(List<String> fileContent) {
        int nbIpsSupportingTls = 0;
        int nbIpsSupportingSsl = 0;

        for (String line : fileContent) {
            if ((this.atLeastOneSequencesHasABBA(line, IPV7_LEFT_PATTERN)
                    || this.atLeastOneSequencesHasABBA(line, IPV7_RIGHT_PATTERN))
                    && !this.atLeastOneSequencesHasABBA(line, IPV7_INSIDE_PATTERN)) {
                nbIpsSupportingTls++;
            }

            if (this.atLeastOneSequenceSupportSsl(line)) {
                nbIpsSupportingSsl++;
            }
        }

        System.out.println(nbIpsSupportingTls);
        System.out.println(nbIpsSupportingSsl);
    }

    private boolean atLeastOneSequencesHasABBA(String line, Pattern ipv7Pattern) {
        return ipv7Pattern.matcher(line).results()
                .map(MatchResult::group)
                .anyMatch(sequence -> hasABBA(sequence, 0));
    }

    private boolean hasABBA(String sequence, int start) {
        int end = start + 3;

        if (end >= sequence.length()) {
            return false;
        }

        if (sequence.charAt(start) == sequence.charAt(end)
                && sequence.charAt(start + 1) == sequence.charAt(end - 1)
                && sequence.charAt(start) != sequence.charAt(start + 1)) {
            return true;
        }

        return this.hasABBA(sequence, start + 1);
    }

    // TODO : finir part 2
    private boolean atLeastOneSequenceSupportSsl(String line) {
        List<String> leftAbaSequences = this.getAllABASequences(line, IPV7_LEFT_PATTERN);
        List<String> rightAbaSequences = this.getAllABASequences(line, IPV7_RIGHT_PATTERN);
        List<String> outsideAbaSequences = Stream.concat(leftAbaSequences.parallelStream(), rightAbaSequences.parallelStream())
                .toList();

        List<String> insideAbaSequences = this.getAllABASequences(line, IPV7_INSIDE_PATTERN);

        // TODO : last result : 163 too low
        //  -> raison potentielle : plusieurs ABA dans une sequence, récupérer toutes les ABA
        return this.isBAB(outsideAbaSequences, insideAbaSequences);
    }

    private List<String> getAllABASequences(String line, Pattern pattern) {
        Matcher matcher = pattern.matcher(line);

        List<String> allAbaSequences = new ArrayList<>();

        while (matcher.find()) {
            String sequence = matcher.group();
            Optional<String> optionalSubSequence = this.getABAInSequence(sequence);
            optionalSubSequence.ifPresent(allAbaSequences::add);
        }

        return allAbaSequences;
    }

    private Optional<String> getABAInSequence(String sequence) {
        int start = 0;
        int end = 2;

        while (end < sequence.length()) {
            if (sequence.charAt(start) == sequence.charAt(end)
                    && sequence.charAt(start) != sequence.charAt(start + 1)) {
                return Optional.of(sequence.substring(start, end + 1));
            }
            start++;
            end++;
        }

        return Optional.empty();
    }

    private boolean isBAB(List<String> outsideAbaSequences, List<String> insideAbaSequences) {
        for (String outside: outsideAbaSequences) {
            for (String inside: insideAbaSequences) {
                if (outside.charAt(0) == inside.charAt(1) && outside.charAt(1) == inside.charAt(0)) {
                    return true;
                }
            }
        }
        return false;
    }
}
