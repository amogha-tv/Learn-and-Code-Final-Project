package org.itt.controller;

import org.itt.model.Feedback;
import org.itt.model.User;
import org.itt.service.FeedbackService;

import java.sql.SQLException;
import java.util.List;

public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController() {
        this.feedbackService = new FeedbackService();
    }

    public void addFeedback(User user, int menuItemId, String comment, int rating, java.sql.Date dateOfFeedback, String itemName) throws SQLException, ClassNotFoundException {
        Feedback feedback = new Feedback(0, user.getUserId(), menuItemId, comment, rating, dateOfFeedback, itemName);
        feedbackService.addFeedback(feedback);
    }

    public List<Feedback> getAllFeedback() throws SQLException, ClassNotFoundException {
        return feedbackService.getAllFeedback();
    }

    public double getAverageRating(int menuItemId) throws SQLException, ClassNotFoundException {
        return feedbackService.getAverageRating(menuItemId);
    }
}
