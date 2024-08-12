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
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "INSERT INTO MenuItem (name, price, availability, menuDate, mealType, isSweet, isSpicy, vegType) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, menuItem.getName());
        statement.setBigDecimal(2, menuItem.getPrice());
        statement.setBoolean(3, menuItem.isAvailability());
        statement.setDate(4, menuItem.getMenuDate());
        statement.setString(5, menuItem.getMealType());
        statement.setBoolean(6, menuItem.isSweet());
        statement.setBoolean(7, menuItem.isSpicy());
        statement.setString(8, menuItem.getVegType());
        statement.executeUpdate();
        statement.close();
    }

    public void updateMenuItem(MenuItem menuItem) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "UPDATE MenuItem SET name = ?, price = ?, availability = ?, menuDate = ?, mealType = ?, isSweet = ?, isSpicy = ?, vegType = ? WHERE menuItemId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
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
        statement.close();
    }

    public List<MenuItem> getAllMenuItems() throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "SELECT * FROM MenuItem";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        List<MenuItem> menuItems = new ArrayList<>();
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
        resultSet.close();
        statement.close();
        return menuItems;
    }

    public MenuItem getMenuItemById(int menuItemId) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "SELECT * FROM MenuItem WHERE menuItemId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, menuItemId);
        ResultSet resultSet = statement.executeQuery();
        MenuItem menuItem = null;
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
        resultSet.close();
        statement.close();
        return menuItem;
    }

    public MenuItem getMenuItemByName(String name) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "SELECT * FROM MenuItem WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        MenuItem menuItem = null;
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
        resultSet.close();
        statement.close();
        return menuItem;
    }

    public void deleteMenuItem(int menuItemId) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "DELETE FROM MenuItem WHERE menuItemId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, menuItemId);
        statement.executeUpdate();
        statement.close();
    }
}
