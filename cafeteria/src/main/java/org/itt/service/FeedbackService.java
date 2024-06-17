package org.itt.service;

import org.itt.dao.FeedbackDAO;
import org.itt.model.Feedback;

import java.sql.SQLException;
import java.util.List;

public class FeedbackService {
    private final FeedbackDAO feedbackDAO;

    public FeedbackService() {
        this.feedbackDAO = new FeedbackDAO();
    }

    public List<Feedback> getAllFeedback() throws SQLException, ClassNotFoundException {
        return feedbackDAO.getAllFeedback();
    }

    public void addFeedback(Feedback feedback) throws SQLException, ClassNotFoundException {
        feedbackDAO.addFeedback(feedback);
    }

    public List<Feedback> getFeedbackForMenuItem(int menuItemId) throws SQLException, ClassNotFoundException {
        return feedbackDAO.getFeedbackForMenuItem(menuItemId);
    }
}
