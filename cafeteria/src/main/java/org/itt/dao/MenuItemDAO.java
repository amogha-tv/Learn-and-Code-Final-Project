package org.itt.dao;

import org.itt.model.MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {

    public void addMenuItem(MenuItem menuItem) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "INSERT INTO MenuItem (name, price, availability, menuDate, mealType, isSweet, isSpicy, vegType) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, menuItem.getName());
                statement.setBigDecimal(2, menuItem.getPrice());
                statement.setBoolean(3, menuItem.isAvailability());
                statement.setDate(4, menuItem.getMenuDate());
                statement.setString(5, menuItem.getMealType());
                statement.setBoolean(6, menuItem.isSweet());
                statement.setBoolean(7, menuItem.isSpicy());
                statement.setString(8, menuItem.getVegType());
                statement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
    }

    public void updateMenuItem(MenuItem menuItem) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "UPDATE MenuItem SET name = ?, price = ?, availability = ?, menuDate = ?, mealType = ?, isSweet = ?, isSpicy = ?, vegType = ? WHERE menuItemId = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, menuItem.getName());
                statement.setBigDecimal(2, menuItem.getPrice());
                statement.setBoolean(3, menuItem.isAvailability());
                statement.setDate(4, menuItem.getMenuDate());
                statement.setString(5, menuItem.getMealType());
                statement.setBoolean(6, menuItem.isSweet());
                statement.setBoolean(7, menuItem.isSpicy());
                statement.setString(8, menuItem.getVegType());
                statement.setInt(9, menuItem.getMenuItemId());
                statement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
    }

    public List<MenuItem> getAllMenuItems() throws SQLException, ClassNotFoundException {
        List<MenuItem> menuItems = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "SELECT * FROM MenuItem";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    MenuItem menuItem = new MenuItem(
                            resultSet.getInt("menuItemId"),
                            resultSet.getString("name"),
                            resultSet.getBigDecimal("price"),
                            resultSet.getBoolean("availability"),
                            resultSet.getDate("menuDate"),
                            resultSet.getString("mealType"),
                            resultSet.getBoolean("isSweet"),
                            resultSet.getBoolean("isSpicy"),
                            resultSet.getString("vegType")
                    );
                    menuItems.add(menuItem);
                }
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
        return menuItems;
    }

    public MenuItem getMenuItemById(int menuItemId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        MenuItem menuItem = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "SELECT * FROM MenuItem WHERE menuItemId = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, menuItemId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        menuItem = new MenuItem(
                                resultSet.getInt("menuItemId"),
                                resultSet.getString("name"),
                                resultSet.getBigDecimal("price"),
                                resultSet.getBoolean("availability"),
                                resultSet.getDate("menuDate"),
                                resultSet.getString("mealType"),
                                resultSet.getBoolean("isSweet"),
                                resultSet.getBoolean("isSpicy"),
                                resultSet.getString("vegType")
                        );
                    }
                }
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
        return menuItem;
    }

    public MenuItem getMenuItemByName(String name) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        MenuItem menuItem = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "SELECT * FROM MenuItem WHERE name = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        menuItem = new MenuItem(
                                resultSet.getInt("menuItemId"),
                                resultSet.getString("name"),
                                resultSet.getBigDecimal("price"),
                                resultSet.getBoolean("availability"),
                                resultSet.getDate("menuDate"),
                                resultSet.getString("mealType"),
                                resultSet.getBoolean("isSweet"),
                                resultSet.getBoolean("isSpicy"),
                                resultSet.getString("vegType")
                        );
                    }
                }
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
        return menuItem;
    }

    public void deleteMenuItem(int menuItemId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "DELETE FROM MenuItem WHERE menuItemId = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, menuItemId);
                statement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
    }
}
