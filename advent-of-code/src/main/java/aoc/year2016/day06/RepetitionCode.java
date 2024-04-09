package aoc.year2016.day06;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class RepetitionCode {

    /**
     * Retrieve message from list with 2 different methods: by finding the most recurring character then the least
     * recurring one.
     *
     * @param messages     Messages received
     * @param isSecondPart Defines if we're testing first or second part of exercise
     */
    public void recoverMessages(List<String> messages, boolean isSecondPart) {
        // If there is no message, we stop the function
        if (messages.isEmpty()) {
            return;
        }

        if (isSecondPart) {
            // Retrieve message with the least recurring characters
            String messageLeastCommon = this.recoverMessage(messages, false);
            log.info("Least common characters: \"{}\"", messageLeastCommon);
        } else {
            // Retrieve message with the most recurring characters
            String messageMostCommon = this.recoverMessage(messages, true);
            log.info("Most common characters: \"{}\"", messageMostCommon);
        }
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
}
