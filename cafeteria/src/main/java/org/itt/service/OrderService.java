package org.itt.service;

import org.itt.dao.OrderDAO;
import org.itt.model.Orders;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
    }

    public void addOrder(Orders order) throws SQLException, ClassNotFoundException {
        orderDAO.addOrder(order);
    }

    public Orders getOrderForFeedback(int userId, int menuItemId) throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderForFeedback(userId, menuItemId);
    }

    public void markOrderFeedbackGiven(int orderId) throws SQLException, ClassNotFoundException {
        orderDAO.markOrderFeedbackGiven(orderId);
    }

    public List<Integer> getRolledOutItems() throws SQLException, ClassNotFoundException {
        return orderDAO.getRolledOutItems();
    }
}
