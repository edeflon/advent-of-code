package aoc.year2023.day05;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WaitForIt {
    record Race(Long time, Long record) {}

    public void numberOfWaysBeatRecords(List<String> fileContent) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcherTime = pattern.matcher(fileContent.get(0));
        Matcher matcherRecords = pattern.matcher(fileContent.get(1));

        List<Race> races = new ArrayList<>();
        while (matcherTime.find() && matcherRecords.find()) {
            races.add(new Race(Long.parseLong(matcherTime.group()), Long.parseLong(matcherRecords.group())));
        }

        // PART 1
        List<Long> wins = new ArrayList<>();
        for (Race race: races) {
            long win = 0L;
            for (int i = 0; i < race.time; i++) {
                long res = i * (race.time - i);
                if (res > race.record) {
                    win++;
                }
            }
            wins.add(win);
        }

        long res = wins.get(0);
        for (int i = 1; i < wins.size(); i++) {
            res *= wins.get(i);
        }

        System.out.println(res);

        // PART 2
        long time = 0;
        long records = 0;
        for (Race race: races) {
            time = Long.parseLong(Long.toString(time) + race.time());
            records = Long.parseLong(Long.toString(records) + race.record());
        }
        Race onlyRace = new Race(time, records);

        long firstWin = -1;
        long i = 0L;
        while (firstWin == -1) {
            long result = i * (onlyRace.time - i);
            if (result > onlyRace.record) {
                firstWin = i;
            }
            i++;
        }

        long lastWin = -1;
        long j = onlyRace.time;
        while (lastWin == -1) {
            long result = j * (onlyRace.time - j);
            if (result > onlyRace.record) {
                lastWin = j;
            }
            j--;
        }

        System.out.println((lastWin - firstWin) + 1); // +1 pour prendre en compte le premier chiffre
    }
}
