package org.itt.service;

import org.itt.dao.UserDAO;
import org.itt.model.User;

import java.sql.SQLException;

public class AuthService {
    private final UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public User authenticate(int userId, String password) throws SQLException, ClassNotFoundException {
        User user = userDAO.getUserByIdAndPassword(userId, password);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean isAuthorized(User user, String requiredRole) {
        return user.getRole().equalsIgnoreCase(requiredRole);
    }

    public void addUser(User user) throws SQLException, ClassNotFoundException {
        userDAO.addUser(user);
    }
}
