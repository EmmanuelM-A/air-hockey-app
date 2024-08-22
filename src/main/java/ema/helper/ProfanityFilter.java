package ema.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class implements a profanity filter.
 * The profanityFilter method is based on code from:
 * https://gist.github.com/PimDeWitte/c04cc17bc5fa9d7e3aee6670d4105941
 * 
 * STILL IN PROGRESS
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
        String[] words = input.split("\\s+");

        for (String word : words) {
            if(inappropriateWords.contains(word.toLowerCase())) {
                return false;
            }
        }

        return true;
    }

}
