package org.itt.service;

import org.itt.dao.MenuItemDAO;
import org.itt.model.MenuItem;

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

    public void addMenuItem(MenuItem menuItem) throws SQLException, ClassNotFoundException {
        menuItemDAO.addMenuItem(menuItem);
    }

    public void updateMenuItem(MenuItem menuItem) throws SQLException, ClassNotFoundException {
        menuItemDAO.updateMenuItem(menuItem);
    }

    public void deleteMenuItem(int menuItemId) throws SQLException, ClassNotFoundException {
        menuItemDAO.deleteMenuItem(menuItemId);
    }
}
