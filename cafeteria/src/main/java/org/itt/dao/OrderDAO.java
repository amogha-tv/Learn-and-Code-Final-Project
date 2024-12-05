package org.itt.dao;

import org.itt.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {

    public void addOrder(Order order) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "INSERT INTO Orders (userId, menuItemId, orderDate, feedbackGiven) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, order.getUserId());
                statement.setInt(2, order.getMenuItemId());
                statement.setDate(3, order.getOrderDate());
                statement.setBoolean(4, order.isFeedbackGiven());
                statement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
    }

    public Order getOrderForFeedback(int userId, int menuItemId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        Order order = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "SELECT * FROM Orders WHERE userId = ? AND menuItemId = ? AND feedbackGiven = false";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userId);
                statement.setInt(2, menuItemId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        order = new Order(
                                resultSet.getInt("orderId"),
                                resultSet.getInt("userId"),
                                resultSet.getInt("menuItemId"),
                                resultSet.getDate("orderDate"),
                                resultSet.getBoolean("feedbackGiven")
                        );
                    }
                }
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
        return order;
    }

    public List<Integer> getRolledOutItems() throws SQLException, ClassNotFoundException {
        List<Integer> rolledOutItems = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "SELECT menuItemId FROM Recommendation";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    rolledOutItems.add(resultSet.getInt("menuItemId"));
                }
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
        return rolledOutItems;
    }

    public void markOrderFeedbackGiven(int orderId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "UPDATE Orders SET feedbackGiven = true WHERE orderId = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, orderId);
                statement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
    }

    public List<Order> getOrdersByUserId(int userId) throws SQLException, ClassNotFoundException {
        List<Order> orders = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "SELECT * FROM Orders WHERE userId = ?";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order(
                            resultSet.getInt("orderId"),
                            resultSet.getInt("userId"),
                            resultSet.getInt("menuItemId"),
                            resultSet.getDate("orderDate"),
                            resultSet.getBoolean("feedbackGiven")
                    );
                    orders.add(order);
                }
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
        return orders;
    }

    public Map<String, Integer> getOrderCountsForItems() throws SQLException, ClassNotFoundException {
        Map<String, Integer> orderCounts = new HashMap<>();
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "SELECT MenuItem.name, COUNT(Orders.menuItemId) AS orderCount FROM Orders " +
                    "JOIN MenuItem ON Orders.menuItemId = MenuItem.menuItemId " +
                    "GROUP BY Orders.menuItemId, MenuItem.name";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String itemName = resultSet.getString("name");
                    int count = resultSet.getInt("orderCount");
                    orderCounts.put(itemName, count);
                }
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
        return orderCounts;
    }

    public void clearOrders() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "DELETE FROM Orders";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
    }
}
