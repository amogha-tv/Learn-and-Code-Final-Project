package org.itt.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SentimentAnalysisTest {
    @BeforeEach
    void setUp() throws IOException {
        try (FileWriter positiveWriter = new FileWriter("src/test/resources/positive_words.txt");
             FileWriter negativeWriter = new FileWriter("src/test/resources/negative_words.txt")) {
            positiveWriter.write("good\n");
            positiveWriter.write("great\n");
            positiveWriter.write("excellent\n");
            positiveWriter.write("amazing\n");
            positiveWriter.write("delicious\n");
            positiveWriter.write("love\n");
            positiveWriter.write("nice\n");
            positiveWriter.write("best\n");

            negativeWriter.write("bad\n");
            negativeWriter.write("terrible\n");
            negativeWriter.write("horrible\n");
            negativeWriter.write("awful\n");
            negativeWriter.write("disgusting\n");
            negativeWriter.write("hate\n");
            negativeWriter.write("worst\n");
            negativeWriter.write("pathetic\n");
        }
    }

    @Test
    void testAnalyzeSentimentPositive() {
        String comment = "The food was excellent and delicious!";
        String sentiment = SentimentAnalysis.analyzeSentiment(comment);
        assertEquals("positive", sentiment);
    }

    @Test
    void testAnalyzeSentimentNegative() {
        String comment = "The food was terrible and disgusting!";
        String sentiment = SentimentAnalysis.analyzeSentiment(comment);
        assertEquals("negative", sentiment);
    }

    @Test
    void testAnalyzeSentimentNeutral() {
        String comment = "The food was okay.";
        String sentiment = SentimentAnalysis.analyzeSentiment(comment);
        assertEquals("neutral", sentiment);
    }
}
