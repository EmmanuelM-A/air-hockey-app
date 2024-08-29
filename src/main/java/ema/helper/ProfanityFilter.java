package ema.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * This class implements a profanity filter.
 */
public class ProfanityFilter {
    /**
     * A set containg a list of inappropriate words
     */
    private Set<String> inappropriateWords;

    /**
     * Creates an instance of the ProfanityFilter with the given data loaded from a file.
     * @param filename The file containing the inapporiate words.
     */
    public ProfanityFilter(String filename) {
        this.inappropriateWords = new HashSet<>();
        loadFile(filename);
    }

    /**
     * Loads the innappropriate words into a set for use later.
     * @param filename The file containing the data.
     */
    private void loadFile(String filename) {
        try (
            BufferedReader br = new BufferedReader(new FileReader(filename));
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                inappropriateWords.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks the inputted text against the data.
     * @param input The text input
     * @return True if no inappropriate words have been found and false otherwise.
     */
    public boolean checkInput(String input) {
        String[] words = input.toLowerCase().split("\\s+");
        for (String word : words) {
            if(inappropriateWords.contains(word.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}
