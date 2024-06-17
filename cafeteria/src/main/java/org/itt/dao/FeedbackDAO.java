package org.itt.dao;

import org.itt.model.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {
    public List<Feedback> getAllFeedback() throws SQLException, ClassNotFoundException {
        List<Feedback> feedbacks = new ArrayList<>();
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "SELECT f.*, m.name AS itemName FROM Feedback f JOIN MenuItem m ON f.menuItemID = m.menuItemID";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int feedbackID = resultSet.getInt("feedbackID");
                int userID = resultSet.getInt("userID");
                int menuItemID = resultSet.getInt("menuItemID");
                String comment = resultSet.getString("comment");
                int rating = resultSet.getInt("rating");
                java.sql.Date dateOfFeedback = resultSet.getDate("dateOfFeedback");
                String itemName = resultSet.getString("itemName");

                Feedback feedback = new Feedback(feedbackID, userID, menuItemID, comment, rating, dateOfFeedback);
                feedback.setItemName(itemName);
                feedbacks.add(feedback);
            }
        }

        return feedbacks;
    }

    public void addFeedback(Feedback feedback) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "INSERT INTO Feedback (userID, menuItemID, comment, rating, dateOfFeedback) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, feedback.getUserId());
            statement.setInt(2, feedback.getMenuItemId());
            statement.setString(3, feedback.getComment());
            statement.setInt(4, feedback.getRating());
            statement.setDate(5, feedback.getDateOfFeedback());

            statement.executeUpdate();
        }
    }

    public List<Feedback> getFeedbackForMenuItem(int menuItemId) throws SQLException, ClassNotFoundException {
        List<Feedback> feedbacks = new ArrayList<>();
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "SELECT * FROM Feedback WHERE menuItemID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, menuItemId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int feedbackID = resultSet.getInt("feedbackID");
                    int userID = resultSet.getInt("userID");
                    String comment = resultSet.getString("comment");
                    int rating = resultSet.getInt("rating");
                    java.sql.Date dateOfFeedback = resultSet.getDate("dateOfFeedback");

                    Feedback feedback = new Feedback(feedbackID, userID, menuItemId, comment, rating, dateOfFeedback);
                    feedbacks.add(feedback);
                }
            }
        }

        return feedbacks;
    }
}
