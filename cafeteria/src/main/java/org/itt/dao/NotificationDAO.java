package org.itt.dao;

import org.itt.model.Notification;
import org.itt.dao.DataBaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    public void addNotification(Notification notification) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "INSERT INTO Notification (userID, message, sentAt) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, notification.getUserId());
            statement.setString(2, notification.getMessage());
            statement.setTimestamp(3, notification.getSentAt());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                notification.setNotificationId(generatedKeys.getInt(1));
            }
        }
    }

    public void addUserNotification(int userId, int notificationId) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "INSERT INTO UserNotification (userID, notificationID) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, notificationId);
            statement.executeUpdate();
        }
    }

    public List<Notification> getUnreadNotifications(int userId) throws SQLException, ClassNotFoundException {
        List<Notification> notifications = new ArrayList<>();
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "SELECT n.* FROM Notification n " +
                "JOIN UserNotification un ON n.notificationID = un.notificationID " +
                "WHERE un.userID = ? AND un.isRead = FALSE";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int notificationId = resultSet.getInt("notificationID");
                    String message = resultSet.getString("message");
                    java.sql.Timestamp sentAt = resultSet.getTimestamp("sentAt");
                    Notification notification = new Notification(notificationId, userId, message, sentAt, false);
                    notifications.add(notification);
                }
            }
        }
        return notifications;
    }

    public void markNotificationsAsRead(int userId) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "UPDATE UserNotification SET isRead = TRUE WHERE userID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }

    public List<Integer> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
        List<Integer> employeeIds = new ArrayList<>();
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "SELECT userID FROM User WHERE roleID = (SELECT roleID FROM Role WHERE roleName = 'Employee')";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                employeeIds.add(resultSet.getInt("userID"));
            }
        }
        return employeeIds;
    }
}
