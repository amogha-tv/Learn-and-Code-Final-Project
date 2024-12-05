package org.itt.dao;

import org.itt.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User getUserByIdAndPassword(int userId, String password) throws SQLException, ClassNotFoundException {
        User user = null;
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "SELECT u.*, r.roleName FROM User u JOIN Role r ON u.roleID = r.roleID WHERE u.userID = ? AND u.password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userId);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("username");
                        String role = resultSet.getString("roleName");
                        user = new User(userId, name, role, password);
                    }
                }
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }

        return user;
    }

    public void addUser(User user) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "INSERT INTO User (username, password, roleID) VALUES (?, ?, (SELECT roleID FROM Role WHERE roleName = ?))";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getRole());

                statement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
    }
}
