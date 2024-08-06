package org.itt.controller;

import org.itt.model.Orders;
import org.itt.service.OrderService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OrderController {
    private final OrderService orderService;

    public OrderController() {
        this.orderService = new OrderService();
    }

    public void addOrder(int userId, int menuItemId, java.sql.Date orderDate) throws SQLException, ClassNotFoundException {
        Orders order = new Orders(0, userId, menuItemId, orderDate, false);
        orderService.addOrder(order);
    }

    public List<Integer> getRolledOutItems() throws SQLException, ClassNotFoundException {
        return orderService.getRolledOutItems();
    }

    public Orders getOrderForFeedback(int userId, int menuItemId) throws SQLException, ClassNotFoundException {
        return orderService.getOrderForFeedback(userId, menuItemId);
    }

    public void markOrderFeedbackGiven(int orderId) throws SQLException, ClassNotFoundException {
        orderService.markOrderFeedbackGiven(orderId);
    }

    public Map<String, Integer> getOrderCountsForItems() throws SQLException, ClassNotFoundException {
        return orderService.getOrderCountsForItems();
    }

    public void clearOrders() throws SQLException, ClassNotFoundException {
        orderService.clearOrders();
    }
}
