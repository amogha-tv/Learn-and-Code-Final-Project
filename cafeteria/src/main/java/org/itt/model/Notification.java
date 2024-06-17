package org.itt.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Notification implements Serializable {
    private int notificationId;
    private int userId;
    private String message;
    private Timestamp sentAt;
    private boolean read;

    public Notification() {}

    public Notification(int notificationId, int userId, String message, Timestamp sentAt, boolean read) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.sentAt = sentAt;
        this.read = read;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return String.format("Notification: You have a new notification:\nMessage: %s\nSent At: %s", message, sentAt);
    }
}
