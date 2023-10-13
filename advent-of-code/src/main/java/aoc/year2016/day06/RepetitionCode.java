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
     * Récupère un message à partir d'une liste de deux manières : en regardant le caractère le plus récurrent puis le
     * moins récurrent.
     *
     * @param filename : nom du fichier où l'input est stocké
     * @throws IOException : exception à jeter en cas d'erreur lors de la lecture du fichier
     */
    public void recoverMessages(String filename) throws IOException {
        // Conversion de l'input en tableau de String
        List<String> messages = this.convertFileDataToArray(filename);

        if (!messages.isEmpty()) {
            // Partie 1 : récupération du message avec les caractères les plus fréquents
            String messageMostCommon = this.recoverMessage(messages, "max");
            // Partie 2 : récupération du message avec les caractères les moins fréquents
            String messageLeastCommon = this.recoverMessage(messages, "min");

            // Affichage des messages corrigés
            LOGGER.log(Level.INFO, "Most common characters: \"{0}\"", messageMostCommon);
            LOGGER.log(Level.INFO, "Least common characters: \"{0}\"", messageLeastCommon);
        }
    }

    /**
     * Récupération d'un message à partir d'une liste de messages en vérifiant le nombre d'occurence des
     * caractères par colonnes. Les caractères avec le plus ou moins d'occurences sont ceux du message original.
     *
     * @param messages : liste des messages reçus
     * @param frequency : "max" ou "min", permet la récupération des caractères plus ou moins fréquents
     * @return le message corrigé
     */
    public String recoverMessage(List<String> messages, String frequency) {
        StringBuilder stringBuilder = new StringBuilder();

        int messageLen = messages.get(0).length();
        // Parcours de chaque caractère pour vérifier les occurences et reconstruire le message initial
        for (int i = 0; i < messageLen; i++) {
            Map<Character, Integer> charCount = new HashMap<>();
            for (String str : messages) {
                charCount.merge(str.charAt(i), 1, Integer::sum);
            }
            // Récupération du caractère le plus ou moins récurrent de la colonne
            char character = frequency.equals("max") ?
                    Collections.max(charCount.entrySet(), Map.Entry.comparingByValue()).getKey()
                    : Collections.min(charCount.entrySet(), Map.Entry.comparingByValue()).getKey();
            stringBuilder.append(character);
        }

        // Retourne le message corrigé
        return stringBuilder.toString();
    }

    /**
     * Converti les données du fichier en argument en tableau de chaînes de caractères
     *
     * @param filename : nom du fichier où l'input est stocké
     * @throws IOException : exception à jeter en cas d'erreur lors de la lecture du fichier
     */
    private List<String> convertFileDataToArray(String filename) throws IOException {
        List<String> messages = new ArrayList<>();
        try (FileReader fr = new FileReader("src/main/resources/inputs/2016/" + filename);
             BufferedReader bf = new BufferedReader(fr)) {
            String line;
            while((line = bf.readLine()) != null){
                messages.add(line);
            }
            return messages;
        }
    }
}
