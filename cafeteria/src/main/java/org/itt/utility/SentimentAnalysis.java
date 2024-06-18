package org.itt.utility;

public class SentimentAnalysis {
    public static String analyzeSentiment(String comment) {
        String lowerCaseComment = comment.toLowerCase();

        int positiveScore = 0;
        int negativeScore = 0;

        String[] positiveWords = {"good", "great", "excellent", "amazing", "delicious", "love", "nice", "best"};
        String[] negativeWords = {"bad", "terrible", "horrible", "awful", "disgusting", "hate", "worst", "pathetic"};

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
