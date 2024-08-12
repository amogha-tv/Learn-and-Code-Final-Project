package org.itt.service;

import org.itt.dao.OrderDAO;
import org.itt.model.Orders;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OrderService {
    private final OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
    }

    public void addOrder(Orders order) throws SQLException, ClassNotFoundException {
        orderDAO.addOrder(order);
    }

    public List<Integer> getRolledOutItems() throws SQLException, ClassNotFoundException {
        return orderDAO.getRolledOutItems();
    }

    public Orders getOrderForFeedback(int userId, int menuItemId) throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderForFeedback(userId, menuItemId);
    }

    public void markOrderFeedbackGiven(int orderId) throws SQLException, ClassNotFoundException {
        orderDAO.markOrderFeedbackGiven(orderId);
    }

    public List<Orders> getOrdersByUserId(int userId) throws SQLException, ClassNotFoundException {
        return orderDAO.getOrdersByUserId(userId);
    }

    public Map<String, Integer> getOrderCountsForItems() throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderCountsForItems();
    }

    public void clearOrders() throws SQLException, ClassNotFoundException {
        orderDAO.clearOrders();
    }
}
