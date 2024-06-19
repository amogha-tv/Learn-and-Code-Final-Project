package org.itt.service;

import org.itt.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotificationServiceTest {
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationService();
    }

    @Test
    void testAddNotification() throws SQLException, ClassNotFoundException {
        Notification notification = new Notification(0, 1, "Test notification", new Timestamp(System.currentTimeMillis()), false, "NEW_ITEM");
        notificationService.addNotification(notification);
        List<Notification> notifications = notificationService.getUnreadNotifications(1);
        assertTrue(notifications.stream().anyMatch(notif -> notif.getMessage().equals("Test notification")));
    }

    @Test
    void testGetUnreadNotifications() throws SQLException, ClassNotFoundException {
        List<Notification> notifications = notificationService.getUnreadNotifications(1);
        assertNotNull(notifications);
    }

    @Test
    void testMarkNotificationsAsRead() throws SQLException, ClassNotFoundException {
        notificationService.markNotificationsAsRead(1);
        List<Notification> notifications = notificationService.getUnreadNotifications(1);
        assertTrue(notifications.isEmpty());
    }

    @Test
    void testAddNotificationForAllEmployees() throws SQLException, ClassNotFoundException {
        Notification notification = new Notification(0, 1, "Test notification for all", new Timestamp(System.currentTimeMillis()), false, "NEW_ITEM");
        notificationService.addNotificationForAllEmployees(notification);
        List<Notification> notifications = notificationService.getUnreadNotifications(1);
        assertTrue(notifications.stream().anyMatch(notif -> notif.getMessage().equals("Test notification for all")));
    }
}
