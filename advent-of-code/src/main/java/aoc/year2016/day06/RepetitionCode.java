package aoc.year2016.day06;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RepetitionCode {

    private String errorCorrectedMessage;
    private List<String> messages;

    private static final Logger LOGGER = Logger.getLogger(RepetitionCode.class.getPackage().getName());

    /**
     * Récupère un message à partir d'une liste de deux manières : en regardant le caractère le plus récurrent puis le
     * moins récurrent.
     *
     * @param filename : nom du fichier où l'input est stocké
     * @throws IOException : exception à jeter en cas d'erreur lors de la lecture du fichier
     */
    public void recoverMessages(String filename) throws IOException {
        this.recoverMessageP1(filename);
        this.recoverMessageP2(filename);
    }

    /**
     * Partie 1 : récupération d'un message à partir d'une liste de messages en vérifiant le nombre d'occurence des
     * caractères par colonnes. Les caractères avec le plus d'occurences sont ceux du message original.
     *
     * @param filename : nom du fichier où l'input est stocké
     * @throws IOException : exception à jeter en cas d'erreur lors de la lecture du fichier
     */
    public void recoverMessageP1(String filename) throws IOException {
        // Conversion de l'input en tableau de String
        this.convertFileDataToArray(filename);

        if (!this.messages.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            int messageLen = this.messages.get(0).length();
            // Parcours de chaque caractère pour vérifier les occurences et reconstruire le message initial
            for (int i = 0; i < messageLen; i++) {
                HashMap<Character, Integer> charCount = new HashMap<>();
                for (String str : this.messages) {
                    if (charCount.containsKey(str.charAt(i))) {
                        int count = charCount.get(str.charAt(i));
                        charCount.put(str.charAt(i), ++count);
                    } else {
                        charCount.put(str.charAt(i), 1);
                    }
                }
                // Récupération du caractère le plus récurrent de la colonne
                char chara = Collections.max(charCount.entrySet(), Map.Entry.comparingByValue()).getKey();
                stringBuilder.append(chara);
            }
            this.errorCorrectedMessage = stringBuilder.toString();
        }

        // Affichage du message corrigé
        LOGGER.log(Level.INFO, "The error-corrected version of the message is \"{0}\"", this.errorCorrectedMessage);
    }

    /**
     * Partie 2 : récupération d'un message à partir d'une liste de messages en vérifiant le nombre d'occurence des
     * caractères par colonnes. Les caractères avec le moins d'occurences sont ceux du message original.
     *
     * @param filename : nom du fichier où l'input est stocké
     * @throws IOException : exception à jeter en cas d'erreur lors de la lecture du fichier
     */
    public void recoverMessageP2(String filename) throws IOException {
        // Conversion de l'input en tableau de String
        this.convertFileDataToArray(filename);

        if (!this.messages.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            int messageLen = this.messages.get(0).length();
            // Parcours de chaque caractère pour vérifier les occurences et reconstruire le message initial
            for (int i = 0; i < messageLen; i++) {
                HashMap<Character, Integer> charCount = new HashMap<>();
                for (String str : this.messages) {
                    if (charCount.containsKey(str.charAt(i))) {
                        int count = charCount.get(str.charAt(i));
                        charCount.put(str.charAt(i), ++count);
                    } else {
                        charCount.put(str.charAt(i), 1);
                    }
                }
                // Récupération du caractère le moins récurrent de la colonne
                char chara = Collections.min(charCount.entrySet(), Map.Entry.comparingByValue()).getKey();
                stringBuilder.append(chara);
            }
            this.errorCorrectedMessage = stringBuilder.toString();
        }

        // Affichage du message corrigé
        LOGGER.log(Level.INFO, "The error-corrected version of the message is \"{0}\"", this.errorCorrectedMessage);
    }

    /**
     * Converti les données du fichier en argument en tableau de chaînes de caractères
     *
     * @param filename : nom du fichier où l'input est stocké
     * @throws IOException : exception à jeter en cas d'erreur lors de la lecture du fichier
     */
    private void convertFileDataToArray(String filename) throws IOException {
        this.messages = new ArrayList<>();
        try (FileReader fr = new FileReader("src/main/resources/inputs/2016/" + filename);
             BufferedReader bf = new BufferedReader(fr)) {
            String line;
            while((line = bf.readLine()) != null){
                this.messages.add(line);
            }
        }
    }
}
