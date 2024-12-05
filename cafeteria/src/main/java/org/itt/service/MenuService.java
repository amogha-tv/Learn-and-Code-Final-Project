package org.itt.service;

import org.itt.dao.MenuItemDAO;
import org.itt.model.MenuItem;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class MenuService {
    private final MenuItemDAO menuItemDAO;

    public MenuService() {
        this.menuItemDAO = new MenuItemDAO();
    }

    public List<MenuItem> getAllMenuItems() throws SQLException, ClassNotFoundException {
        return menuItemDAO.getAllMenuItems();
    }

    public void addMenuItem(String name, BigDecimal price, boolean availability, java.sql.Date menuDate, String mealType, boolean isSweet, boolean isSpicy, String vegType) throws SQLException, ClassNotFoundException {
        MenuItem menuItem = new MenuItem(0, name, price, availability, menuDate, mealType, isSweet, isSpicy, vegType);
        menuItemDAO.addMenuItem(menuItem);
    }

    public void updateMenuItem(int menuItemId, String name, BigDecimal price, boolean availability, java.sql.Date menuDate, String mealType, boolean isSweet, boolean isSpicy, String vegType) throws SQLException, ClassNotFoundException {
        MenuItem menuItem = new MenuItem(menuItemId, name, price, availability, menuDate, mealType, isSweet, isSpicy, vegType);
        menuItemDAO.updateMenuItem(menuItem);
    }

    public void deleteMenuItem(int menuItemId) throws SQLException, ClassNotFoundException {
        menuItemDAO.deleteMenuItem(menuItemId);
    }

    public MenuItem getMenuItemByName(String name) throws SQLException, ClassNotFoundException {
        return menuItemDAO.getMenuItemByName(name);
    }

    public MenuItem getMenuItemById(int menuItemId) throws SQLException, ClassNotFoundException {
        return menuItemDAO.getMenuItemById(menuItemId);
    }
}
