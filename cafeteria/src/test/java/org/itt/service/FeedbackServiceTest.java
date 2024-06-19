package org.itt.service;

import org.itt.model.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackServiceTest {
    private FeedbackService feedbackService;

    @BeforeEach
    void setUp() {
        feedbackService = new FeedbackService();
    }

    @Test
    void testAddFeedback() throws SQLException, ClassNotFoundException {
        Feedback feedback = new Feedback(0, 1, 1, "Great food", 5, java.sql.Date.valueOf("2024-01-01"));
        feedbackService.addFeedback(feedback);
        List<Feedback> feedbacks = feedbackService.getAllFeedback();
        assertTrue(feedbacks.stream().anyMatch(fb -> fb.getComment().equals("Great food")));
    }

    @Test
    void testGetAllFeedback() throws SQLException, ClassNotFoundException {
        List<Feedback> feedbacks = feedbackService.getAllFeedback();
        assertNotNull(feedbacks);
        assertFalse(feedbacks.isEmpty());
    }

    @Test
    void testGetAverageRating() throws SQLException, ClassNotFoundException {
        double averageRating = feedbackService.getAverageRating(1);
        assertTrue(averageRating >= 0.0);
    }
}
