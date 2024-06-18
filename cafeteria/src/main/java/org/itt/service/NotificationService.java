package org.itt.service;

import org.itt.dao.NotificationDAO;
import org.itt.model.Notification;
import org.itt.utility.Server;

import java.sql.SQLException;
import java.util.List;

public class NotificationService {
    private final NotificationDAO notificationDAO;

    public NotificationService() {
        this.notificationDAO = new NotificationDAO();
    }

    public void addNotification(Notification notification) throws SQLException, ClassNotFoundException {
        notificationDAO.addNotification(notification);
        Server.notifyClients("New Notification: " + notification.getMessage());
    }

    public List<Notification> getUnreadNotifications(int userId) throws SQLException, ClassNotFoundException {
        return notificationDAO.getUnreadNotifications(userId);
    }

    public void markNotificationsAsRead(int userId) throws SQLException, ClassNotFoundException {
        notificationDAO.markNotificationsAsRead(userId);
    }

    public void addNotificationForAllEmployees(Notification notification) throws SQLException, ClassNotFoundException {
        notificationDAO.addNotificationForAllEmployees(notification);
        Server.notifyClients("New Notification: " + notification.getMessage());
    }
}
