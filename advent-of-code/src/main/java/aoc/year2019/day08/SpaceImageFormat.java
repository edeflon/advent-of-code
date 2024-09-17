package aoc.year2019.day08;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SpaceImageFormat {
    private static final int WIDTH = 25;
    private static final int HEIGHT = 6;

    /**
     * Answer to both parts of day 8 of year 2019 :
     * - Find layer with less 0 digits then multiply number of 1 digits with number of 2 digits of this layer ;
     * - Decode and print encoded message.
     *
     * @param fileContent Content of argument file
     */
    public void findLayerAndMultipliedDigits(List<String> fileContent) {
        Image image = convertFileContentToImage(fileContent.get(0));

        Layer layerWithLess0Digits = image.getLayers().stream()
                .min(Comparator.comparing(layer -> layer.countDigits(0)))
                .orElseThrow(NoSuchElementException::new);

        int nbOf1Digits = layerWithLess0Digits.countDigits(1);
        int nbOf2Digits = layerWithLess0Digits.countDigits(2);

        int result = nbOf1Digits * nbOf2Digits;

        log.info("{} is the number of 1 digits multiplied by the number of 2 digits.", result);

        image.print(image.decode(), WIDTH);
    }

    /**
     * Convert file content to Image
     *
     * @param fileContent Content of argument file
     * @return Image with its layers
     */
    private Image convertFileContentToImage(String fileContent) {
        int start = 0;
        int end = WIDTH * HEIGHT;

        List<Layer> layers = new ArrayList<>();
        while (end <= fileContent.length()) {
            Layer layer = new Layer(new ArrayList<>());
            String pixels = fileContent.substring(start, end);

            Pattern pattern = Pattern.compile("\\d");
            Matcher matcher = pattern.matcher(pixels);
            while (matcher.find()) {
                layer.getPixels().add(Integer.parseInt(matcher.group(0)));
            }

            layers.add(layer);
            start = end;
            end = end + (WIDTH * HEIGHT);
        }

        return new Image(layers);
    }
}
