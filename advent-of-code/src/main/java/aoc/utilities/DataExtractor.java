package aoc.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataExtractor {

    /**
     * Convert data of given file as a list of String
     *
     * @param filename Name of the file containing the data
     * @return Data file as list of String
     * @throws IOException Exception thrown when an error is catch while reading the file
     */
    public List<String> convertFileDataToString(String filename) throws IOException {
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/inputs/" + filename))) {
            String line;
            while ((line = bf.readLine()) != null) {
                fileContent.add(line);
            }
            return fileContent;
        }
    }
}
