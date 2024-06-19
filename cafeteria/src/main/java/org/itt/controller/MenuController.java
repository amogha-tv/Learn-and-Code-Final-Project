package org.itt.controller;

import org.itt.model.MenuItem;
import org.itt.service.MenuService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class MenuController {
    private final MenuService menuService;

    public MenuController() {
        this.menuService = new MenuService();
    }

    public void addMenuItem(String name, BigDecimal price, boolean availability, java.sql.Date menuDate, String mealType) throws SQLException, ClassNotFoundException {
        MenuItem menuItem = new MenuItem(0, name, price, availability, menuDate, mealType);
        menuService.addMenuItem(menuItem);
    }

    public void updateMenuItem(int menuItemId, String name, BigDecimal price, boolean availability, java.sql.Date menuDate, String mealType) throws SQLException, ClassNotFoundException {
        MenuItem menuItem = new MenuItem(menuItemId, name, price, availability, menuDate, mealType);
        menuService.updateMenuItem(menuItem);
    }

    public void deleteMenuItem(int menuItemId) throws SQLException, ClassNotFoundException {
        menuService.deleteMenuItem(menuItemId);
    }

    public List<MenuItem> getAllMenuItems() throws SQLException, ClassNotFoundException {
        return menuService.getAllMenuItems();
    }

    public MenuItem getMenuItemByName(String itemName) throws SQLException, ClassNotFoundException {
        return menuService.getMenuItemByName(itemName);
    }
}
