package org.itt.dao;

import org.itt.model.Feedback;
import org.itt.dao.DataBaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {
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

    public List<Feedback> getAllFeedback() throws SQLException, ClassNotFoundException {
        List<Feedback> feedbacks = new ArrayList<>();
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "SELECT Feedback.*, MenuItem.name AS itemName FROM Feedback INNER JOIN MenuItem ON Feedback.menuItemID = MenuItem.menuItemID";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int feedbackId = resultSet.getInt("feedbackID");
                int userId = resultSet.getInt("userID");
                int menuItemId = resultSet.getInt("menuItemID");
                String comment = resultSet.getString("comment");
                int rating = resultSet.getInt("rating");
                java.sql.Date dateOfFeedback = resultSet.getDate("dateOfFeedback");
                String itemName = resultSet.getString("itemName");
                Feedback feedback = new Feedback(feedbackId, userId, menuItemId, comment, rating, dateOfFeedback, itemName);
                feedbacks.add(feedback);
            }
        }
        return feedbacks;
    }

    public double getAverageRating(int menuItemId) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "SELECT AVG(rating) AS averageRating FROM Feedback WHERE menuItemID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, menuItemId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("averageRating");
                }
            }
        }
        return 0.0;
    }
}
