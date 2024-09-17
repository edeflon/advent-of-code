package aoc.year2019.day08;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class Image {
    private List<Layer> layers;

    /**
     * Decode layers to find message in image
     * @return Number list corresponding to message
     */
    public List<Integer> decode() {
        int layerLength = layers.get(0).getPixels().size();

        List<Integer> decodedImage = new ArrayList<>();
        for (int i = 0; i < layerLength; i++) {
            for (Layer layer : layers) {
                int pixel = layer.getPixels().get(i);
                if (0 == pixel || 1 == pixel) {
                    decodedImage.add(pixel);
                    break;
                }
            }
        }

        return decodedImage;
    }

    /**
     * Display message of decoded image
     *
     * @param decodedImage Numbers representing message of decoded image
     * @param width Image width
     */
    public void print(List<Integer> decodedImage, int width) {
        int count = 0;
        for (int pixel : decodedImage) {
            if (1 == pixel) {
                System.out.print("O");
            } else {
                System.out.print(" ");
            }

            count++;
            if (count == width) {
                count = 0;
                System.out.println("");
            }
        }
    }
}
