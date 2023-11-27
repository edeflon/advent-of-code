package aoc.year2022.day06;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class TuningTrouble {

    /**
     * Find how many characters need to be processed before the first start-of-packet marker is detected.
     *
     * @param filename     Name of the file containing the data
     * @param isSecondPart Check if we're testing first or second part of exercise
     * @throws IOException Exception thrown when an error is catch while reading the file
     */
    public void countCharactersBeforeStartOfPacket(String filename, boolean isSecondPart) throws IOException {
        String datastream = this.convertFileDataToString(filename);

        int markerSize = isSecondPart ? 14 : 4;

        int firstMarkerIndex = -1;
        for (int i = 0; i < datastream.length(); i++) {
            Set<Character> characters = new HashSet<>();
            boolean hasDuplicates = false;
            for (int j = i; j < i + markerSize && j < datastream.length(); j++) {
                if (!hasDuplicates && !characters.add(datastream.charAt(j))) {
                    hasDuplicates = true;
                }
            }
            if (!hasDuplicates) {
                firstMarkerIndex = i + markerSize;
                break;
            }
        }

        log.info("First marker after character {}: {}", firstMarkerIndex, datastream);
    }

    /**
     * Convert data of given file in a string
     *
     * @param filename Name of the file containing the data
     * @return Message received as a string
     * @throws IOException Exception thrown when an error is catch while reading the file
     */
    private String convertFileDataToString(String filename) throws IOException {
        try (BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/inputs/" + filename))) {
            return bf.readLine();
        }
    }
}
