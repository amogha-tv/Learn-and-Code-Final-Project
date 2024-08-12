package org.itt.dao;

import org.itt.model.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {

    public void addOrder(Orders order) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "INSERT INTO Orders (userId, menuItemId, orderDate, feedbackGiven) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, order.getUserId());
        statement.setInt(2, order.getMenuItemId());
        statement.setDate(3, order.getOrderDate());
        statement.setBoolean(4, order.isFeedbackGiven());
        statement.executeUpdate();
        statement.close();
    }

    public Orders getOrderForFeedback(int userId, int menuItemId) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "SELECT * FROM Orders WHERE userId = ? AND menuItemId = ? AND feedbackGiven = false";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.setInt(2, menuItemId);
        ResultSet resultSet = statement.executeQuery();
        Orders order = null;
        if (resultSet.next()) {
            order = new Orders(
                    resultSet.getInt("orderId"),
                    resultSet.getInt("userId"),
                    resultSet.getInt("menuItemId"),
                    resultSet.getDate("orderDate"),
                    resultSet.getBoolean("feedbackGiven")
            );
        }
        resultSet.close();
        statement.close();
        return order;
    }

    public List<Integer> getRolledOutItems() throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "SELECT menuItemId FROM Recommendation";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        List<Integer> rolledOutItems = new ArrayList<>();
        while (resultSet.next()) {
            rolledOutItems.add(resultSet.getInt("menuItemId"));
        }
        resultSet.close();
        statement.close();
        return rolledOutItems;
    }

    public void markOrderFeedbackGiven(int orderId) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "UPDATE Orders SET feedbackGiven = true WHERE orderId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, orderId);
        statement.executeUpdate();
        statement.close();
    }

    public List<Orders> getOrdersByUserId(int userId) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "SELECT * FROM Orders WHERE userId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        List<Orders> orders = new ArrayList<>();
        while (resultSet.next()) {
            Orders order = new Orders(
                    resultSet.getInt("orderId"),
                    resultSet.getInt("userId"),
                    resultSet.getInt("menuItemId"),
                    resultSet.getDate("orderDate"),
                    resultSet.getBoolean("feedbackGiven")
            );
            orders.add(order);
        }
        resultSet.close();
        statement.close();
        return orders;
    }

    public Map<String, Integer> getOrderCountsForItems() throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "SELECT MenuItem.name, COUNT(Orders.menuItemId) AS orderCount FROM Orders " +
                "JOIN MenuItem ON Orders.menuItemId = MenuItem.menuItemId " +
                "GROUP BY Orders.menuItemId, MenuItem.name";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        Map<String, Integer> orderCounts = new HashMap<>();
        while (resultSet.next()) {
            String itemName = resultSet.getString("name");
            int count = resultSet.getInt("orderCount");
            orderCounts.put(itemName, count);
        }

        resultSet.close();
        statement.close();
        return orderCounts;
    }

    public void clearOrders() throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "DELETE FROM Orders";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
        statement.close();
    }
}
