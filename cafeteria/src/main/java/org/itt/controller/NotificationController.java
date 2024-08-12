package org.itt.controller;

import org.itt.model.Notification;
import org.itt.service.NotificationService;

import java.sql.SQLException;
import java.util.List;

public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController() {
        this.notificationService = new NotificationService();
    }

    public List<Notification> getUnreadNotifications(int userId) throws SQLException, ClassNotFoundException {
        return notificationService.getUnreadNotifications(userId);
    }

    public void markNotificationsAsRead(int userId) throws SQLException, ClassNotFoundException {
        notificationService.markNotificationsAsRead(userId);
    }

    public void addNotificationForAllEmployees(Notification notification) throws SQLException, ClassNotFoundException {
        notificationService.addNotificationForAllEmployees(notification);
    }
}
