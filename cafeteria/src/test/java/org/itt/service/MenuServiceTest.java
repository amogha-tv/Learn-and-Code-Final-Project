package org.itt.service;

import org.itt.model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuServiceTest {
    private MenuService menuService;

    @BeforeEach
    void setUp() {
        menuService = new MenuService();
    }

    @Test
    void testAddMenuItem() throws SQLException, ClassNotFoundException {
        MenuItem menuItem = new MenuItem(0, "Test Item", new BigDecimal("10.00"), true, java.sql.Date.valueOf("2024-01-01"), "BREAKFAST");
        menuService.addMenuItem(menuItem);
        List<MenuItem> menuItems = menuService.getAllMenuItems();
        assertTrue(menuItems.stream().anyMatch(item -> item.getName().equals("Test Item")));
    }

    @Test
    void testUpdateMenuItem() throws SQLException, ClassNotFoundException {
        MenuItem menuItem = new MenuItem(0, "Test Item", new BigDecimal("10.00"), true, java.sql.Date.valueOf("2024-01-01"), "BREAKFAST");
        menuService.addMenuItem(menuItem);
        menuItem.setName("Updated Test Item");
        menuService.updateMenuItem(menuItem);
        List<MenuItem> menuItems = menuService.getAllMenuItems();
        assertTrue(menuItems.stream().anyMatch(item -> item.getName().equals("Updated Test Item")));
    }

    @Test
    void testDeleteMenuItem() throws SQLException, ClassNotFoundException {
        MenuItem menuItem = new MenuItem(0, "Test Item", new BigDecimal("10.00"), true, java.sql.Date.valueOf("2024-01-01"), "BREAKFAST");
        menuService.addMenuItem(menuItem);
        menuService.deleteMenuItem(menuItem.getMenuItemId());
        List<MenuItem> menuItems = menuService.getAllMenuItems();
        assertFalse(menuItems.stream().anyMatch(item -> item.getName().equals("Test Item")));
    }

    @Test
    void testGetAllMenuItems() throws SQLException, ClassNotFoundException {
        List<MenuItem> menuItems = menuService.getAllMenuItems();
        assertNotNull(menuItems);
        assertFalse(menuItems.isEmpty());
    }
}
