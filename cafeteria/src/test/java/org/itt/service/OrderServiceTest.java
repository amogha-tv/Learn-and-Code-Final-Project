package org.itt.service;

import org.itt.model.Orders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();
    }

    @Test
    void testAddOrder() throws SQLException, ClassNotFoundException {
        Orders order = new Orders(0, 1, 1, java.sql.Date.valueOf("2024-01-01"), false);
        orderService.addOrder(order);
        List<Orders> orders = orderService.getOrdersByUserId(1);
        assertTrue(orders.stream().anyMatch(ord -> ord.getMenuItemId() == 1));
    }

    @Test
    void testGetRolledOutItems() throws SQLException, ClassNotFoundException {
        List<Integer> rolledOutItems = orderService.getRolledOutItems();
        assertNotNull(rolledOutItems);
    }

    @Test
    void testGetOrderForFeedback() throws SQLException, ClassNotFoundException {
        Orders order = new Orders(0, 1, 1, java.sql.Date.valueOf("2024-01-01"), false);
        orderService.addOrder(order);
        Orders fetchedOrder = orderService.getOrderForFeedback(1, 1);
        assertNotNull(fetchedOrder);
    }

    @Test
    void testMarkOrderFeedbackGiven() throws SQLException, ClassNotFoundException {
        Orders order = new Orders(0, 1, 1, java.sql.Date.valueOf("2024-01-01"), false);
        orderService.addOrder(order);
        orderService.markOrderFeedbackGiven(order.getOrderId());
        Orders fetchedOrder = orderService.getOrderForFeedback(1, 1);
        assertNull(fetchedOrder);
    }
}
