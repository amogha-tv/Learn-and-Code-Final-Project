package org.itt.dao;

import org.itt.model.MenuItem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuItemDAO {

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

                MenuItem menuItem = new MenuItem(menuItemId, name, price, availability, menuDate);
                menuItems.add(menuItem);
            }
        }

        return menuItems;
    }

    public void addMenuItem(MenuItem menuItem) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "INSERT INTO MenuItem (name, price, availability, menuDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, menuItem.getName());
            statement.setBigDecimal(2, menuItem.getPrice());
            statement.setBoolean(3, menuItem.isAvailability());
            statement.setDate(4, new java.sql.Date(menuItem.getMenuDate().getTime()));

            statement.executeUpdate();
        }
    }

    public void updateMenuItem(MenuItem menuItem) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "UPDATE MenuItem SET name = ?, price = ?, availability = ?, menuDate = ? WHERE menuItemID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, menuItem.getName());
            statement.setBigDecimal(2, menuItem.getPrice());
            statement.setBoolean(3, menuItem.isAvailability());
            statement.setDate(4, new java.sql.Date(menuItem.getMenuDate().getTime()));
            statement.setInt(5, menuItem.getMenuItemId());

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
}
