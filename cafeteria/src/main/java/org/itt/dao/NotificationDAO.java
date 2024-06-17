package org.itt.dao;

import org.itt.model.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    public List<Notification> getAllNotifications() throws SQLException, ClassNotFoundException {
        List<Notification> notifications = new ArrayList<>();
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "SELECT * FROM Notification";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("notificationID");
                int userId = resultSet.getInt("userID");
                String message = resultSet.getString("message");
                java.sql.Timestamp sentAt = resultSet.getTimestamp("sentAt");

                notifications.add(new Notification(id, userId, message, sentAt));
            }
        }

        return notifications;
    }

    public void addNotification(Notification notification) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();

        String query = "INSERT INTO Notification (userID, message, sentAt) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, notification.getUserId());
            statement.setString(2, notification.getMessage());
            statement.setTimestamp(3, notification.getSentAt());

            statement.executeUpdate();
        }
    }
}
