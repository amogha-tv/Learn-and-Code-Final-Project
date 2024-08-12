package org.itt.controller;

import org.itt.model.User;
import org.itt.service.AuthService;

import java.sql.SQLException;

public class AuthController {
    private final AuthService authService;

    public AuthController() {
        this.authService = new AuthService();
    }

    public User authenticate(int userId, String password) throws SQLException, ClassNotFoundException {
        return authService.authenticate(userId, password);
    }

    public boolean isAuthorized(User user, String role) {
        return authService.isAuthorized(user, role);
    }

    public void addUser(User user) throws SQLException, ClassNotFoundException {
        authService.addUser(user);
    }
}
