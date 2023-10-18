package aoc.year2016.day06;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RepetitionCode {

    private static final Logger LOGGER = Logger.getLogger(RepetitionCode.class.getPackage().getName());

    /**
     * Retrieve message from list with 2 different methods: by finding the most recurring character then the least
     * recurring one.
     *
     * @param filename : name of the file where data is stored
     * @throws IOException : exception to throw when an error is catch while reading a file
     */
    public void recoverMessages(String filename) throws IOException {
        // Convert data input in String array
        List<String> messages = this.convertFileDataToArray(filename);

        // If no message are found, we stop the function
        if (messages.isEmpty()) {
            return;
        }

        boolean isMostCommon = true;
        // Part 1 : retrieve message with the most recurring characters
        String messageMostCommon = this.recoverMessage(messages, isMostCommon);
        // Part 2 : retrieve message with the least recurring characters
        isMostCommon = false; // We're looking for the least recurring characters
        String messageLeastCommon = this.recoverMessage(messages, isMostCommon);

        // Displaying the results of both methods
        LOGGER.log(Level.INFO, "Most common characters: \"{0}\"", messageMostCommon);
        LOGGER.log(Level.INFO, "Least common characters: \"{0}\"", messageLeastCommon);
    }

    /**
     * Recovery of a message from message list by checking the characters occurrence by column.
     * Characters with most or least frequency are the ones of the original message.
     *
     * @param messages     : list of the messages we received
     * @param isMostCommon : boolean which allows to retrieve our characters, true = most common, false = least common
     * @return corrected message
     */
    public String recoverMessage(List<String> messages, boolean isMostCommon) {
        StringBuilder stringBuilder = new StringBuilder();

        int messageLen = messages.get(0).length();
        // Check occurrence of each character and rebuild the original message
        for (int i = 0; i < messageLen; i++) {
            Map<Character, Integer> charCount = new HashMap<>();
            for (String str : messages) {
                charCount.merge(str.charAt(i), 1, Integer::sum);
            }
            // Retrieve most or least recurring character of the column
            char character = isMostCommon ?
                    Collections.max(charCount.entrySet(), Map.Entry.comparingByValue()).getKey()
                    : Collections.min(charCount.entrySet(), Map.Entry.comparingByValue()).getKey();
            stringBuilder.append(character);
        }

        // Return corrected message
        return stringBuilder.toString();
    }

    /**
     * Convert given file data in string array.
     *
     * @param filename : name of the file where data is stored
     * @throws IOException : exception to throw when an error is catch while reading a file
     */
    private List<String> convertFileDataToArray(String filename) throws IOException {
        List<String> messages = new ArrayList<>();
        try (FileReader fr = new FileReader("src/main/resources/inputs/2016/" + filename);
             BufferedReader bf = new BufferedReader(fr)) {
            String line;
            while ((line = bf.readLine()) != null) {
                messages.add(line);
            }
            return messages;
        }
    }
}
