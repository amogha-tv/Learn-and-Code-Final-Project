package org.itt.dao;

import org.itt.model.Orders;
import org.itt.dao.DataBaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public void addOrder(Orders order) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "INSERT INTO Orders (userID, menuItemID, orderDate, feedbackGiven) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, order.getUserId());
            statement.setInt(2, order.getMenuItemId());
            statement.setDate(3, order.getOrderDate());
            statement.setBoolean(4, order.isFeedbackGiven());
            statement.executeUpdate();
        }
    }

    public Orders getOrderForFeedback(int userId, int menuItemId) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "SELECT * FROM Orders WHERE userID = ? AND menuItemID = ? AND feedbackGiven = FALSE";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, menuItemId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int orderId = resultSet.getInt("orderID");
                    java.sql.Date orderDate = resultSet.getDate("orderDate");
                    boolean feedbackGiven = resultSet.getBoolean("feedbackGiven");
                    return new Orders(orderId, userId, menuItemId, orderDate, feedbackGiven);
                }
            }
        }
        return null;
    }

    public void markOrderFeedbackGiven(int orderId) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "UPDATE Orders SET feedbackGiven = TRUE WHERE orderID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        }
    }

    public List<Integer> getRolledOutItems() throws SQLException, ClassNotFoundException {
        List<Integer> rolledOutItems = new ArrayList<>();
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "SELECT menuItemID FROM Recommendation";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                rolledOutItems.add(resultSet.getInt("menuItemID"));
            }
        }
        return rolledOutItems;
    }
}
