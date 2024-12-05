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

    public List<MenuItem> getAllMenuItems() throws SQLException, ClassNotFoundException {
        return menuService.getAllMenuItems();
    }

    public void addMenuItem(String name, BigDecimal price, boolean availability, java.sql.Date menuDate, String mealType, boolean isSweet, boolean isSpicy, String vegType) throws SQLException, ClassNotFoundException {
        menuService.addMenuItem(name, price, availability, menuDate, mealType, isSweet, isSpicy, vegType);
    }

    public void updateMenuItem(int menuItemId, String name, BigDecimal price, boolean availability, java.sql.Date menuDate, String mealType, boolean isSweet, boolean isSpicy, String vegType) throws SQLException, ClassNotFoundException {
        menuService.updateMenuItem(menuItemId, name, price, availability, menuDate, mealType, isSweet, isSpicy, vegType);
    }

    public void deleteMenuItem(int menuItemId) throws SQLException, ClassNotFoundException {
        menuService.deleteMenuItem(menuItemId);
    }

    public MenuItem getMenuItemByName(String name) throws SQLException, ClassNotFoundException {
        return menuService.getMenuItemByName(name);
    }

    public MenuItem getMenuItemById(int menuItemId) throws SQLException, ClassNotFoundException {
        return menuService.getMenuItemById(menuItemId);
    }
}
