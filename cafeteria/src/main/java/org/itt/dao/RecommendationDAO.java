package org.itt.dao;

import org.itt.model.Recommendation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecommendationDAO {

    public void addRecommendation(Recommendation recommendation) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "INSERT INTO Recommendation (menuItemID, recommendedBy, recommendationDate, mealType) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, recommendation.getMenuItemId());
                statement.setInt(2, recommendation.getRecommendedBy());
                statement.setDate(3, recommendation.getRecommendationDate());
                statement.setString(4, recommendation.getMealType());
                statement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
    }

    public void clearRecommendations() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "DELETE FROM Recommendation";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
    }

    public List<Recommendation> getAllRecommendations() throws SQLException, ClassNotFoundException {
        List<Recommendation> recommendations = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "SELECT r.*, m.name AS itemName, u.username AS recommenderName, m.mealType " +
                    "FROM Recommendation r " +
                    "JOIN MenuItem m ON r.menuItemID = m.menuItemID " +
                    "JOIN User u ON r.recommendedBy = u.userID";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int recommendationId = resultSet.getInt("recommendationID");
                    int menuItemId = resultSet.getInt("menuItemID");
                    int recommendedBy = resultSet.getInt("recommendedBy");
                    Date recommendationDate = resultSet.getDate("recommendationDate");
                    String itemName = resultSet.getString("itemName");
                    String recommenderName = resultSet.getString("recommenderName");
                    String mealType = resultSet.getString("mealType");
                    Recommendation recommendation = new Recommendation(recommendationId, menuItemId, recommendedBy, recommendationDate, itemName, recommenderName, mealType);
                    recommendations.add(recommendation);
                }
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
        return recommendations;
    }
}
