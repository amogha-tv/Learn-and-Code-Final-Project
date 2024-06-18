package org.itt.utility;

import org.itt.model.*;
import org.itt.service.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CafeteriaApp {
    private final AuthService authService;
    private final MenuService menuService;
    private final FeedbackService feedbackService;
    private final NotificationService notificationService;
    private final RecommendationService recommendationService;
    private final RecommendationEngine recommendationEngine;
    private final Scanner scanner;

    public CafeteriaApp() {
        this.authService = new AuthService();
        this.menuService = new MenuService();
        this.feedbackService = new FeedbackService();
        this.notificationService = new NotificationService();
        this.recommendationService = new RecommendationService();
        this.recommendationEngine = new RecommendationEngine();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        try {
            System.out.print("Enter User ID: ");
            int userId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            User user = authService.authenticate(userId, password);
            if (user != null) {
                System.out.println("Authenticated user: " + user.getName());
                System.out.println("Role: " + user.getRole());

                while (true) {
                    int optionIndex = 1;
                    System.out.println("Select an option:");

                    System.out.println(optionIndex++ + ". View Menu Items");
                    System.out.println(optionIndex++ + ". View Feedback");

                    if (authService.isAuthorized(user, "Employee")) {
                        System.out.println(optionIndex++ + ". View Notifications");
                        System.out.println(optionIndex++ + ". View Recommendations");
                        System.out.println(optionIndex++ + ". Add Feedback");
                    }

                    if (authService.isAuthorized(user, "Admin")) {
                        System.out.println(optionIndex++ + ". Add Menu Item");
                        System.out.println(optionIndex++ + ". Update Menu Item");
                        System.out.println(optionIndex++ + ". Delete Menu Item");
                        System.out.println(optionIndex++ + ". Add User");
                    }

                    if (authService.isAuthorized(user, "Chef")) {
                        System.out.println(optionIndex++ + ". Roll Out Recommendations");
                        System.out.println(optionIndex++ + ". Generate Feedback Report");
                    }

                    System.out.println("0. Exit");

                    int option = scanner.nextInt();
                    scanner.nextLine(); 

                    int currentIndex = 1;

                    if (option == currentIndex++) {
                        viewMenuItems();
                    } else if (option == currentIndex++) {
                        viewFeedback();
                    } else if (authService.isAuthorized(user, "Employee") && option == currentIndex++) {
                        viewUnreadNotifications(user.getUserId());
                    } else if (authService.isAuthorized(user, "Employee") && option == currentIndex++) {
                        viewRecommendations();
                    } else if (authService.isAuthorized(user, "Employee") && option == currentIndex++) {
                        addFeedback(user);
                    } else if (authService.isAuthorized(user, "Admin") && option == currentIndex++) {
                        addMenuItem(user);
                    } else if (authService.isAuthorized(user, "Admin") && option == currentIndex++) {
                        updateMenuItem(user);
                    } else if (authService.isAuthorized(user, "Admin") && option == currentIndex++) {
                        deleteMenuItem(user);
                    } else if (authService.isAuthorized(user, "Admin") && option == currentIndex++) {
                        addUser(user);
                    } else if (authService.isAuthorized(user, "Chef") && option == currentIndex++) {
                        rollOutRecommendations(user);
                    } else if (authService.isAuthorized(user, "Chef") && option == currentIndex++) {
                        generateFeedbackReport();
                    } else if (option == 0) {
                        System.out.println("Exiting...");
                        System.exit(0);
                    } else {
                        System.out.println("Invalid option.");
                    }
                }
            } else {
                System.out.println("Authentication failed.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void viewMenuItems() throws SQLException, ClassNotFoundException {
        List<MenuItem> menuItems = menuService.getAllMenuItems();
        displayMenuItems(menuItems);
    }

    private void viewFeedback() throws SQLException, ClassNotFoundException {
        List<Feedback> feedbacks = feedbackService.getAllFeedback();
        displayFeedbacks(feedbacks);
    }

    private void viewUnreadNotifications(int userId) throws SQLException, ClassNotFoundException {
        List<Notification> notifications = notificationService.getUnreadNotifications(userId);
        displayNotifications(notifications);
        notificationService.markNotificationsAsRead(userId);
    }

    private void viewRecommendations() throws SQLException, ClassNotFoundException {
        List<Recommendation> recommendations = recommendationService.getAllRecommendations();
        List<Recommendation> filteredRecommendations = recommendationEngine.getFilteredRecommendations(recommendations);
        displayRecommendations(filteredRecommendations);
    }

    private void addFeedback(User user) throws SQLException, ClassNotFoundException {
        System.out.print("Enter Menu Item ID: ");
        int feedbackMenuItemId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Comment: ");
        String comment = scanner.nextLine();
        System.out.print("Enter Rating: ");
        int rating = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Feedback Date (yyyy-mm-dd): ");
        String feedbackDateStr = scanner.nextLine();
        java.sql.Date dateOfFeedback = java.sql.Date.valueOf(feedbackDateStr);
        Feedback feedback = new Feedback(0, user.getUserId(), feedbackMenuItemId, comment, rating, dateOfFeedback);
        feedbackService.addFeedback(feedback);
        Notification notification = new Notification(0, user.getUserId(), "Feedback added for Menu Item ID: " + feedbackMenuItemId, new java.sql.Timestamp(System.currentTimeMillis()), false);
        notificationService.addNotificationForAllEmployees(notification);
        System.out.println("Feedback added.");
    }

    private void addMenuItem(User user) throws SQLException, ClassNotFoundException {
        System.out.print("Enter Menu Item Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Price: ");
        BigDecimal price = scanner.nextBigDecimal();
        System.out.print("Enter Availability (true/false): ");
        boolean availability = scanner.nextBoolean();
        scanner.nextLine();
        System.out.print("Enter Menu Date (yyyy-mm-dd): ");
        String dateStr = scanner.nextLine();
        java.sql.Date menuDate = java.sql.Date.valueOf(dateStr);
        MenuItem menuItem = new MenuItem(0, name, price, availability, menuDate);
        menuService.addMenuItem(menuItem);
        Notification notification = new Notification(0, user.getUserId(), "Menu Item added: " + name, new java.sql.Timestamp(System.currentTimeMillis()), false);
        notificationService.addNotificationForAllEmployees(notification);
        System.out.println("Menu Item added.");
    }

    private void updateMenuItem(User user) throws SQLException, ClassNotFoundException {
        System.out.print("Enter Menu Item ID to update: ");
        int menuItemId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Menu Item Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Price: ");
        BigDecimal price = scanner.nextBigDecimal();
        System.out.print("Enter Availability (true/false): ");
        boolean availability = scanner.nextBoolean();
        scanner.nextLine();
        System.out.print("Enter Menu Date (yyyy-mm-dd): ");
        String dateStr = scanner.nextLine();
        java.sql.Date menuDate = java.sql.Date.valueOf(dateStr);
        MenuItem menuItem = new MenuItem(menuItemId, name, price, availability, menuDate);
        menuService.updateMenuItem(menuItem);
        Notification notification = new Notification(0, user.getUserId(), "Menu Item updated: " + name, new java.sql.Timestamp(System.currentTimeMillis()), false);
        notificationService.addNotificationForAllEmployees(notification);
        System.out.println("Menu Item updated.");
    }

    private void deleteMenuItem(User user) throws SQLException, ClassNotFoundException {
        System.out.print("Enter Menu Item ID to delete: ");
        int menuItemId = scanner.nextInt();
        menuService.deleteMenuItem(menuItemId);
        Notification notification = new Notification(0, user.getUserId(), "Menu Item deleted: " + menuItemId, new java.sql.Timestamp(System.currentTimeMillis()), false);
        notificationService.addNotificationForAllEmployees(notification);
        System.out.println("Menu Item deleted.");
    }

    private void addUser(User user) throws SQLException, ClassNotFoundException {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String userPassword = scanner.nextLine();
        System.out.print("Enter Role (Admin/Chef/Employee): ");
        String role = scanner.nextLine();
        User newUser = new User(0, username, role, userPassword);
        authService.addUser(newUser);
        Notification notification = new Notification(0, user.getUserId(), "New user added: " + username, new java.sql.Timestamp(System.currentTimeMillis()), false);
        notificationService.addNotificationForAllEmployees(notification);
        System.out.println("User added.");
    }

    private void rollOutRecommendations(User user) throws SQLException, ClassNotFoundException {
        recommendationService.clearRecommendations();

        System.out.print("Enter number of items to recommend: ");
        int numberOfItems = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Menu Date for recommendations (yyyy-mm-dd): ");
        String dateStr = scanner.nextLine();
        java.sql.Date recommendationDate = java.sql.Date.valueOf(dateStr);
        List<Integer> menuItemIds = new ArrayList<>();
        for (int i = 0; i < numberOfItems; i++) {
            System.out.print("Enter Menu Item ID to recommend: ");
            int menuItemId = scanner.nextInt();
            scanner.nextLine();
            Recommendation recommendation = new Recommendation(0, menuItemId, user.getUserId(), recommendationDate);
            recommendationService.addRecommendation(recommendation);
            menuItemIds.add(menuItemId);
        }
        Notification notification = new Notification(0, user.getUserId(), "Recommendations added for the next day", new java.sql.Timestamp(System.currentTimeMillis()), false);
        notificationService.addNotificationForAllEmployees(notification);
        System.out.println("Recommendations processed.");
    }

    private void generateFeedbackReport() throws SQLException, ClassNotFoundException {
        List<Feedback> allFeedback = feedbackService.getAllFeedback();
        System.out.println("Monthly Feedback Report:");
        displayFeedbacks(allFeedback);
    }

    private void displayMenuItems(List<MenuItem> menuItems) {
        System.out.printf("%-5s %-20s %-10s %-15s %-10s%n", "ID", "Name", "Price", "Availability", "Menu Date");
        System.out.println("--------------------------------------------------------------------");
        for (MenuItem menuItem : menuItems) {
            System.out.printf("%-5d %-20s %-10.2f %-15s %-10s%n", menuItem.getMenuItemId(), menuItem.getName(), menuItem.getPrice(), menuItem.isAvailability(), menuItem.getMenuDate());
        }
    }

    private void displayFeedbacks(List<Feedback> feedbacks) {
        System.out.printf("%-5s %-20s %-10s %-15s %-50s %-10s%n", "ID", "Item Name", "User ID", "Rating", "Comment", "Date");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        for (Feedback feedback : feedbacks) {
            System.out.printf("%-5d %-20s %-10d %-15d %-50s %-10s%n", feedback.getFeedbackId(), feedback.getItemName(), feedback.getUserId(), feedback.getRating(), feedback.getComment(), feedback.getDateOfFeedback());
        }
    }

    private void displayNotifications(List<Notification> notifications) {
        System.out.printf("%-50s %-30s%n", "Message", "Sent At");
        System.out.println("--------------------------------------------------");
        for (Notification notification : notifications) {
            System.out.printf("%-50s %-30s%n", notification.getMessage(), notification.getSentAt());
        }
    }

    private void displayRecommendations(List<Recommendation> recommendations) {
        System.out.printf("%-5s %-20s %-20s %-15s%n", "ID", "Item Name", "Recommended By", "Date");
        System.out.println("--------------------------------------------------------------------");
        for (Recommendation recommendation : recommendations) {
            System.out.printf("%-5d %-20s %-20s %-15s%n", recommendation.getRecommendationId(), recommendation.getItemName(), recommendation.getRecommenderName(), recommendation.getRecommendationDate());
        }
    }
}
