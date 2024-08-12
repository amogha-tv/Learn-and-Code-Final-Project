package org.itt.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class SentimentAnalysis {
    private static final Set<String> positiveWords = new HashSet<>();
    private static final Set<String> negativeWords = new HashSet<>();

    static {
        loadWords("/positive_words.txt", positiveWords);
        loadWords("/negative_words.txt", negativeWords);
    }

    private static void loadWords(String fileName, Set<String> words) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(SentimentAnalysis.class.getResourceAsStream(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Error reading " + fileName + ": " + e.getMessage());
        }
    }

    public static String analyzeSentiment(String comment) {
        String lowerCaseComment = comment.toLowerCase();

        int positiveScore = 0;
        int negativeScore = 0;

        for (String word : positiveWords) {
            if (lowerCaseComment.contains(word)) {
                positiveScore++;
            }
        }

        for (String word : negativeWords) {
            if (lowerCaseComment.contains(word)) {
                negativeScore++;
            }
        }

        if (positiveScore > negativeScore) {
            return "positive";
        } else if (negativeScore > positiveScore) {
            return "negative";
        } else {
            return "neutral";
        }
    }
}
