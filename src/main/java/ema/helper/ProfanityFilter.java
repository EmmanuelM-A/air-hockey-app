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
    private Set<String> inappropriateWords;

    public ProfanityFilter(String filename) {
        this.inappropriateWords = new HashSet<>();
        loadFile(filename);
    }

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
