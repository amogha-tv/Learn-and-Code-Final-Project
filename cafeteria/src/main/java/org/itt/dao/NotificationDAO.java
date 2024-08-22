package org.itt.dao;

import org.itt.model.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public void addNotification(Notification notification) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "INSERT INTO Notification (userID, message, sentAt, `read`, type) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, notification.getUserId());
                statement.setString(2, notification.getMessage());
                statement.setTimestamp(3, notification.getSentAt());
                statement.setBoolean(4, notification.isRead());
                statement.setString(5, notification.getType());
                statement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
    }

    public List<Notification> getUnreadNotifications(int userId) throws SQLException, ClassNotFoundException {
        List<Notification> notifications = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "SELECT * FROM Notification WHERE userID = ? AND `read` = FALSE AND type IN ('RECOMMENDATION', 'NEW_ITEM', 'AVAILABILITY_CHANGE')";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int notificationId = resultSet.getInt("notificationID");
                        String message = resultSet.getString("message");
                        java.sql.Timestamp sentAt = resultSet.getTimestamp("sentAt");
                        boolean read = resultSet.getBoolean("read");
                        String type = resultSet.getString("type");
                        notifications.add(new Notification(notificationId, userId, message, sentAt, read, type));
                    }
                }
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
        return notifications;
    }

    public void markNotificationsAsRead(int userId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "UPDATE Notification SET `read` = TRUE WHERE userID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, userId);
                statement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
    }

    public void addNotificationForAllEmployees(Notification notification) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DataBaseConnector.getInstance().getConnection();
            String query = "INSERT INTO Notification (userID, message, sentAt, `read`, type) SELECT userID, ?, ?, ?, ? FROM User WHERE roleID = 3";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, notification.getMessage());
                statement.setTimestamp(2, notification.getSentAt());
                statement.setBoolean(3, notification.isRead());
                statement.setString(4, notification.getType());
                statement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                DataBaseConnector.getInstance().closeConnection();
            }
        }
    }
}
