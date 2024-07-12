package ema.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements a profanity filter.
 * The profanityFilter method is based on code from:
 * https://gist.github.com/PimDeWitte/c04cc17bc5fa9d7e3aee6670d4105941
 * 
 * STILL IN PROGRESS
 */
public class ProfanityFilter {
    static Map<String, String[]> words = new HashMap<>();
    static int largestWordLength = 0;

    public static void loadConfigs() {
        try {
            URI uri = new URI("https://docs.google.com/spreadsheets/d/1hIEi2YG3ydav1E06Bzf2mQbGZ12kh2fe4ISgLg_UBuM/export?format=csv");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            String line = "";
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                counter++;
                String[] content = null;
                try {
                    content = line.split(",");
                    if (content.length == 0) {
                        continue;
                    }
                    String word = content[0];
                    String[] ignore_in_combination_with_words = new String[]{};
                    if (content.length > 1) {
                        ignore_in_combination_with_words = content[1].split("_");
                    }

                    if (word.length() > largestWordLength) {
                        largestWordLength = word.length();
                    }
                    words.put(word.replaceAll(" ", ""), ignore_in_combination_with_words);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            System.out.println("Loaded " + counter + " words to filter out");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Iterates over a String input and checks whether a cuss word was found in a list, then checks if the word should be ignored (e.g. bass contains the word *ss).
     *
     * @param input
     * @return
     */
    public static boolean containsBadWords(String input) {
        if (input == null) {
            return false;
        }

        // don't forget to remove leetspeak, probably want to move this to its own function and use regex if you want to use this
        input = input.replaceAll("1", "i");
        input = input.replaceAll("!", "i");
        input = input.replaceAll("3", "e");
        input = input.replaceAll("4", "a");
        input = input.replaceAll("@", "a");
        input = input.replaceAll("5", "s");
        input = input.replaceAll("7", "t");
        input = input.replaceAll("0", "o");
        input = input.replaceAll("9", "g");

        input = input.toLowerCase().replaceAll("[^a-zA-Z]", "");

        // iterate over each letter in the word
        for (int start = 0; start < input.length(); start++) {
            // from each letter, keep going to find bad words until either the end of the sentence is reached, or the max word length is reached.
            for (int offset = 1; offset < (input.length() + 1 - start) && offset <= largestWordLength; offset++) {
                String wordToCheck = input.substring(start, start + offset);
                if (words.containsKey(wordToCheck)) {
                    // for example, if you want to say the word bass, that should be possible.
                    String[] ignoreCheck = words.get(wordToCheck);
                    boolean ignore = false;
                    for (String s : ignoreCheck) {
                        if (input.contains(s)) {
                            ignore = true;
                            break;
                        }
                    }
                    if (!ignore) {
                        System.out.println(wordToCheck + " qualified as a bad word in the input");
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static String filterText(String input) {
        if (containsBadWords(input)) {
            return "This message was blocked because a bad word was found. If you believe this word should not be blocked, please message support.";
        }
        return input;
    }
}
