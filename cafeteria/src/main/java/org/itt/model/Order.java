package org.itt.model;

import java.io.Serializable;
import java.sql.Date;

public class Order implements Serializable {
    private int orderId;
    private int userId;
    private int menuItemId;
    private Date orderDate;
    private boolean feedbackGiven;

    public Order() {}

    public Order(int orderId, int userId, int menuItemId, Date orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.menuItemId = menuItemId;
        this.orderDate = orderDate;
    }

    public Order(int orderId, int userId, int menuItemId, Date orderDate, boolean feedbackGiven) {
        this.orderId = orderId;
        this.userId = userId;
        this.menuItemId = menuItemId;
        this.orderDate = orderDate;
        this.feedbackGiven = feedbackGiven;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isFeedbackGiven() {
        return feedbackGiven;
    }

    public void setFeedbackGiven(boolean feedbackGiven) {
        this.feedbackGiven = feedbackGiven;
    }
}
