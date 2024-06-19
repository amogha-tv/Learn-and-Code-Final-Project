package org.itt.service;

import org.itt.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService();
    }

    @Test
    void testAuthenticate() throws SQLException, ClassNotFoundException {
        User user = authService.authenticate(1, "password");
        assertNotNull(user);
        assertEquals(1, user.getUserId());
    }

    @Test
    void testIsAuthorizedAdmin() {
        User user = new User(1, "admin", "Admin", "password");
        assertTrue(authService.isAuthorized(user, "Admin"));
    }

    @Test
    void testIsAuthorizedChef() {
        User user = new User(2, "chef", "Chef", "password");
        assertTrue(authService.isAuthorized(user, "Chef"));
    }

    @Test
    void testIsAuthorizedEmployee() {
        User user = new User(3, "employee", "Employee", "password");
        assertTrue(authService.isAuthorized(user, "Employee"));
    }

    @Test
    void testAddUser() throws SQLException, ClassNotFoundException {
        User newUser = new User(0, "newuser", "Employee", "password");
        authService.addUser(newUser);
        User fetchedUser = authService.authenticate(newUser.getUserId(), "password");
        assertNotNull(fetchedUser);
        assertEquals("newuser", fetchedUser.getName());
    }
}
