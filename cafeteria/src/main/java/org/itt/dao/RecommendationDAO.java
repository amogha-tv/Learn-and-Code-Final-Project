package org.itt.dao;

import org.itt.model.Recommendation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecommendationDAO {
    public List<Recommendation> getAllRecommendations() throws SQLException, ClassNotFoundException {
        List<Recommendation> recommendations = new ArrayList<>();
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "SELECT r.*, m.name AS itemName, u.username AS recommenderName " +
                "FROM Recommendation r " +
                "JOIN MenuItem m ON r.menuItemID = m.menuItemID " +
                "JOIN User u ON r.recommendedBy = u.userID";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int recommendationID = resultSet.getInt("recommendationID");
                int menuItemID = resultSet.getInt("menuItemID");
                int recommendedBy = resultSet.getInt("recommendedBy");
                java.sql.Date recommendationDate = resultSet.getDate("recommendationDate");
                String itemName = resultSet.getString("itemName");
                String recommenderName = resultSet.getString("recommenderName");

                Recommendation recommendation = new Recommendation(recommendationID, menuItemID, recommendedBy, recommendationDate);
                recommendation.setItemName(itemName);
                recommendation.setRecommenderName(recommenderName);
                recommendations.add(recommendation);
            }
        }

        return recommendations;
    }

    public void addRecommendation(Recommendation recommendation) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "INSERT INTO Recommendation (menuItemID, recommendedBy, recommendationDate) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, recommendation.getMenuItemId());
            statement.setInt(2, recommendation.getRecommendedBy());
            statement.setDate(3, recommendation.getRecommendationDate());

            statement.executeUpdate();
        }
    }
}
