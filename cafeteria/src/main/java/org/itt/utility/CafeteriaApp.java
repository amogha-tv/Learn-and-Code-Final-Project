package org.itt.utility;

import org.itt.model.*;
import org.itt.service.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CafeteriaApp {
    private final AuthService authService;
    private final MenuService menuService;
    private final FeedbackService feedbackService;
    private final NotificationService notificationService;
    private final RecommendationService recommendationService;
    private final RecommendationEngine recommendationEngine;
    private final OrderService orderService;
    private final Scanner scanner;

    public CafeteriaApp() {
        this.authService = new AuthService();
        this.menuService = new MenuService();
        this.feedbackService = new FeedbackService();
        this.notificationService = new NotificationService();
        this.recommendationService = new RecommendationService();
        this.recommendationEngine = new RecommendationEngine();
        this.orderService = new OrderService();
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
                    try {
                        int optionIndex = 1;
                        System.out.println("Select an option:");

                        System.out.println(optionIndex++ + ". View Menu Items");
                        System.out.println(optionIndex++ + ". View Feedback");

                        if (authService.isAuthorized(user, "Employee")) {
                            System.out.println(optionIndex++ + ". View Notifications");
                            System.out.println(optionIndex++ + ". View Recommendations");
                            System.out.println(optionIndex++ + ". View Rolled Out Items");
                            System.out.println(optionIndex++ + ". Order Item");
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
                            viewRolledOutItems();
                        } else if (authService.isAuthorized(user, "Employee") && option == currentIndex++) {
                            orderItem(user);
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
                            break;
                        } else {
                            System.out.println("Invalid option. Please select a valid option.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid option.");
                        scanner.nextLine();
                    }
                }
            } else {
                System.out.println("Authentication failed. Please check your User ID and Password.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while connecting to the database. Please try again.");
            e.printStackTrace();
        }
    }

    private void viewMenuItems() {
        try {
            List<MenuItem> menuItems = menuService.getAllMenuItems();
            displayMenuItems(menuItems);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while fetching menu items. Please try again.");
            e.printStackTrace();
        }
    }

    private void viewFeedback() {
        try {
            List<Feedback> feedbacks = feedbackService.getAllFeedback();
            displayFeedbacks(feedbacks);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while fetching feedback. Please try again.");
            e.printStackTrace();
        }
    }

    private void viewUnreadNotifications(int userId) {
        try {
            List<Notification> notifications = notificationService.getUnreadNotifications(userId);
            displayNotifications(notifications);
            notificationService.markNotificationsAsRead(userId);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while fetching notifications. Please try again.");
            e.printStackTrace();
        }
    }

    private void viewRecommendations() {
        try {
            List<Recommendation> recommendations = recommendationService.getAllRecommendations();
            List<Recommendation> filteredRecommendations = recommendationEngine.getFilteredRecommendations(recommendations);
            displayRecommendations(filteredRecommendations);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while fetching recommendations. Please try again.");
            e.printStackTrace();
        }
    }

    private void viewRolledOutItems() {
        try {
            List<Recommendation> recommendations = recommendationService.getAllRecommendations();
            displayRecommendations(recommendations);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while fetching rolled out items. Please try again.");
            e.printStackTrace();
        }
    }

    private void orderItem(User user) {
        try {
            List<Integer> rolledOutItems = orderService.getRolledOutItems();
            System.out.print("Enter Menu Item ID to order: ");
            int menuItemId = scanner.nextInt();
            scanner.nextLine();

            if (!rolledOutItems.contains(menuItemId)) {
                System.out.println("You can only order rolled out items.");
                return;
            }

            System.out.print("Enter Order Date (yyyy-mm-dd): ");
            String orderDateStr = scanner.nextLine();
            java.sql.Date orderDate = java.sql.Date.valueOf(orderDateStr);
            Orders order = new Orders(0, user.getUserId(), menuItemId, orderDate, false);
            orderService.addOrder(order);
            System.out.println("Item ordered successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid details.");
            scanner.nextLine();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while ordering the item. Please try again.");
            e.printStackTrace();
        }
    }

    private void addFeedback(User user) {
        try {
            System.out.print("Enter Menu Item ID: ");
            int feedbackMenuItemId = scanner.nextInt();
            scanner.nextLine();

            Orders order = orderService.getOrderForFeedback(user.getUserId(), feedbackMenuItemId);
            if (order == null) {
                System.out.println("You can only give feedback on items you have ordered and not yet provided feedback for.");
                return;
            }

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
            orderService.markOrderFeedbackGiven(order.getOrderId());
            Notification notification = new Notification(0, user.getUserId(), "Feedback added for Menu Item ID: " + feedbackMenuItemId, new java.sql.Timestamp(System.currentTimeMillis()), false, "FEEDBACK");
            notificationService.addNotificationForAllEmployees(notification);
            System.out.println("Feedback added.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid details.");
            scanner.nextLine();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while adding feedback. Please try again.");
            e.printStackTrace();
        }
    }

    private void addMenuItem(User user) {
        try {
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
            Notification notification = new Notification(0, user.getUserId(), "Menu Item added: " + name, new java.sql.Timestamp(System.currentTimeMillis()), false, "NEW_ITEM");
            notificationService.addNotificationForAllEmployees(notification);
            System.out.println("Menu Item added.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid details.");
            scanner.nextLine();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while adding the menu item. Please try again.");
            e.printStackTrace();
        }
    }

    private void updateMenuItem(User user) {
        try {
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
            Notification notification = new Notification(0, user.getUserId(), "Menu Item updated: " + name, new java.sql.Timestamp(System.currentTimeMillis()), false, "AVAILABILITY_CHANGE");
            notificationService.addNotificationForAllEmployees(notification);
            System.out.println("Menu Item updated.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid details.");
            scanner.nextLine();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while updating the menu item. Please try again.");
            e.printStackTrace();
        }
    }

    private void deleteMenuItem(User user) {
        try {
            System.out.print("Enter Menu Item ID to delete: ");
            int menuItemId = scanner.nextInt();
            menuService.deleteMenuItem(menuItemId);
            Notification notification = new Notification(0, user.getUserId(), "Menu Item deleted: " + menuItemId, new java.sql.Timestamp(System.currentTimeMillis()), false, "AVAILABILITY_CHANGE");
            notificationService.addNotificationForAllEmployees(notification);
            System.out.println("Menu Item deleted.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid Menu Item ID.");
            scanner.nextLine();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while deleting the menu item. Please try again.");
            e.printStackTrace();
        }
    }

    private void addUser(User user) {
        try {
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String userPassword = scanner.nextLine();
            System.out.print("Enter Role (Admin/Chef/Employee): ");
            String role = scanner.nextLine();
            User newUser = new User(0, username, role, userPassword);
            authService.addUser(newUser);
            Notification notification = new Notification(0, user.getUserId(), "New user added: " + username, new java.sql.Timestamp(System.currentTimeMillis()), false, "NEW_USER");
            notificationService.addNotificationForAllEmployees(notification);
            System.out.println("User added.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid details.");
            scanner.nextLine();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while adding the user. Please try again.");
            e.printStackTrace();
        }
    }

    private void rollOutRecommendations(User user) {
        try {
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
            Notification notification = new Notification(0, user.getUserId(), "Recommendations added for the next day", new java.sql.Timestamp(System.currentTimeMillis()), false, "RECOMMENDATION");
            notificationService.addNotificationForAllEmployees(notification);
            System.out.println("Recommendations processed.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid details.");
            scanner.nextLine();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while rolling out recommendations. Please try again.");
            e.printStackTrace();
        }
    }

    private void generateFeedbackReport() {
        try {
            List<Feedback> allFeedback = feedbackService.getAllFeedback();
            System.out.println("Monthly Feedback Report:");
            displayFeedbacks(allFeedback);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while generating the feedback report. Please try again.");
            e.printStackTrace();
        }
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
