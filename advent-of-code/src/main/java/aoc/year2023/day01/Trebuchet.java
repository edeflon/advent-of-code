package aoc.year2023.day01;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Trebuchet {
    public void sumOfAllCalibration(List<String> fileContent) {
        int sum = 0;
        Map<String, Integer> digits = new HashMap<>();
        digits.put("twone", 21);
        digits.put("oneight", 18);
        digits.put("threeight", 38);
        digits.put("fiveight", 58);
        digits.put("sevenine", 79);
        digits.put("nineight", 98);
        digits.put("eightwo", 82);
        digits.put("eighthree", 83);
        digits.put("one", 1);
        digits.put("two", 2);
        digits.put("three", 3);
        digits.put("four", 4);
        digits.put("five", 5);
        digits.put("six", 6);
        digits.put("seven", 7);
        digits.put("eight", 8);
        digits.put("nine", 9);

        for (String line : fileContent) {
            Matcher matcher = Pattern.compile("(\\d|twone|oneight|threeight|fiveight|sevenine|nineight|eightwo|eighthree|one|two|three|four|five|six|seven|eight|nine)?").matcher(line);
            StringBuilder numbersAsString = new StringBuilder();
            while (matcher.find()) {
                String foundNumber = matcher.group();
                if (digits.containsKey(foundNumber)) {
                    numbersAsString.append(digits.get(foundNumber));
                } else {
                    numbersAsString.append(foundNumber);
                }
            }
            String number = numbersAsString.charAt(0) + String.valueOf(numbersAsString.charAt(numbersAsString.length() - 1));
            sum += Integer.parseInt(number);
        }
        System.out.println(sum);
    }
}
