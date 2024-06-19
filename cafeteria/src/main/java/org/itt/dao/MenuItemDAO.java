package org.itt.dao;

import org.itt.model.MenuItem;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {
    public void addMenuItem(MenuItem menuItem) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "INSERT INTO MenuItem (name, price, availability, menuDate, mealType) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, menuItem.getName());
            statement.setBigDecimal(2, menuItem.getPrice());
            statement.setBoolean(3, menuItem.isAvailability());
            statement.setDate(4, menuItem.getMenuDate());
            statement.setString(5, menuItem.getMealType());
            statement.executeUpdate();
        }
    }

    public void updateMenuItem(MenuItem menuItem) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "UPDATE MenuItem SET name = ?, price = ?, availability = ?, menuDate = ?, mealType = ? WHERE menuItemID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, menuItem.getName());
            statement.setBigDecimal(2, menuItem.getPrice());
            statement.setBoolean(3, menuItem.isAvailability());
            statement.setDate(4, menuItem.getMenuDate());
            statement.setString(5, menuItem.getMealType());
            statement.setInt(6, menuItem.getMenuItemId());
            statement.executeUpdate();
        }
    }

    public void deleteMenuItem(int menuItemId) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "DELETE FROM MenuItem WHERE menuItemID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, menuItemId);
            statement.executeUpdate();
        }
    }

    public MenuItem getMenuItem(int menuItemId) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "SELECT * FROM MenuItem WHERE menuItemID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, menuItemId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    BigDecimal price = resultSet.getBigDecimal("price");
                    boolean availability = resultSet.getBoolean("availability");
                    Date menuDate = resultSet.getDate("menuDate");
                    String mealType = resultSet.getString("mealType");
                    return new MenuItem(menuItemId, name, price, availability, menuDate, mealType);
                }
            }
        }
        return null;
    }

    public MenuItem getMenuItemByName(String name) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "SELECT * FROM MenuItem WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int menuItemId = resultSet.getInt("menuItemID");
                    BigDecimal price = resultSet.getBigDecimal("price");
                    boolean availability = resultSet.getBoolean("availability");
                    Date menuDate = resultSet.getDate("menuDate");
                    String mealType = resultSet.getString("mealType");
                    return new MenuItem(menuItemId, name, price, availability, menuDate, mealType);
                }
            }
        }
        return null;
    }

    public List<MenuItem> getAllMenuItems() throws SQLException, ClassNotFoundException {
        List<MenuItem> menuItems = new ArrayList<>();
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "SELECT * FROM MenuItem";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int menuItemId = resultSet.getInt("menuItemID");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                boolean availability = resultSet.getBoolean("availability");
                Date menuDate = resultSet.getDate("menuDate");
                String mealType = resultSet.getString("mealType");
                MenuItem menuItem = new MenuItem(menuItemId, name, price, availability, menuDate, mealType);
                menuItems.add(menuItem);
            }
        }
        return menuItems;
    }
}
