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
                System.out.println("\n*********************************");
                System.out.println("*********************************");
                System.out.println("=================================");
                System.out.println("       WELCOME TO CAFETERIA        ");
                System.out.println("=================================");
                System.out.println("*********************************");
                System.out.println("*********************************");
                System.out.println("");

                System.out.println("\n*********************************");
                System.out.println("   Hello, " + user.getName() + " (" + user.getRole() + ")");
                System.out.println("*********************************");

                if (authService.isAuthorized(user, "Chef")) {
                    viewTopRatedItems();
                }

                while (true) {
                    try {
                        int optionIndex = 1;
                        System.out.println("\n=================================");
                        System.out.println("          Main Menu");
                        System.out.println("=================================");
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
                        System.out.println("=================================");
                        System.out.print("Select an option: ");

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
            System.out.println("\n=================================");
            System.out.println("         Menu Items");
            System.out.println("=================================");
            displayMenuItems(menuItems);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while fetching menu items. Please try again.");
            e.printStackTrace();
        }
    }

    private void viewFeedback() {
        try {
            List<Feedback> feedbacks = feedbackService.getAllFeedback();
            System.out.println("\n=================================");
            System.out.println("          Feedback");
            System.out.println("=================================");
            displayFeedbacks(feedbacks);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while fetching feedback. Please try again.");
            e.printStackTrace();
        }
    }

    private void viewUnreadNotifications(int userId) {
        try {
            List<Notification> notifications = notificationService.getUnreadNotifications(userId);
            System.out.println("\n=================================");
            System.out.println("        Unread Notifications");
            System.out.println("=================================");
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
            System.out.println("\n=================================");
            System.out.println("       Recommendations");
            System.out.println("=================================");
            displayRecommendations(filteredRecommendations);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while fetching recommendations. Please try again.");
            e.printStackTrace();
        }
    }

    private void viewRolledOutItems() {
        try {
            System.out.println("\n=================================");
            System.out.println("        Rolled Out Items");
            System.out.println("=================================");
            System.out.println("Select a meal type to view rolled out items:");
            System.out.println("1. Breakfast");
            System.out.println("2. Lunch");
            System.out.println("3. Dinner");
            System.out.println("4. Beverage");
            int mealTypeOption = scanner.nextInt();
            scanner.nextLine();

            String mealType;
            switch (mealTypeOption) {
                case 1:
                    mealType = "BREAKFAST";
                    break;
                case 2:
                    mealType = "LUNCH";
                    break;
                case 3:
                    mealType = "DINNER";
                    break;
                case 4:
                    mealType = "BEVERAGE";
                    break;
                default:
                    System.out.println("Invalid option. Returning to main menu.");
                    return;
            }

            List<Recommendation> recommendations = recommendationService.getAllRecommendations();
            List<Recommendation> filteredRecommendations = new ArrayList<>();
            for (Recommendation recommendation : recommendations) {
                if (recommendation.getMealType().equalsIgnoreCase(mealType)) {
                    filteredRecommendations.add(recommendation);
                }
            }
            displayRecommendations(filteredRecommendations);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while fetching rolled out items. Please try again.");
            e.printStackTrace();
        }
    }

    private void orderItem(User user) {
        try {
            List<Integer> rolledOutItems = orderService.getRolledOutItems();
            System.out.println("\n=================================");
            System.out.println("         Order Item");
            System.out.println("=================================");
            System.out.print("Enter Menu Item Name to order: ");
            String itemName = scanner.nextLine();

            MenuItem menuItem = menuService.getMenuItemByName(itemName);
            if (menuItem == null || !rolledOutItems.contains(menuItem.getMenuItemId())) {
                System.out.println("You can only order rolled out items by their name.");
                return;
            }

            System.out.print("Enter Order Date (yyyy-mm-dd): ");
            String orderDateStr = scanner.nextLine();
            java.sql.Date orderDate = java.sql.Date.valueOf(orderDateStr);
            Orders order = new Orders(0, user.getUserId(), menuItem.getMenuItemId(), orderDate, false);
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
            System.out.println("\n=================================");
            System.out.println("         Add Feedback");
            System.out.println("=================================");
            System.out.print("Enter Menu Item Name: ");
            String itemName = scanner.nextLine();

            MenuItem menuItem = menuService.getMenuItemByName(itemName);
            if (menuItem == null) {
                System.out.println("Invalid Menu Item Name.");
                return;
            }

            Orders order = orderService.getOrderForFeedback(user.getUserId(), menuItem.getMenuItemId());
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
            Feedback feedback = new Feedback(0, user.getUserId(), menuItem.getMenuItemId(), comment, rating, dateOfFeedback, itemName);
            feedbackService.addFeedback(feedback);
            orderService.markOrderFeedbackGiven(order.getOrderId());
            Notification notification = new Notification(0, user.getUserId(), "Feedback added for Menu Item: " + itemName, new java.sql.Timestamp(System.currentTimeMillis()), false, "FEEDBACK");
            notificationService.addNotificationForAllEmployees(notification);
            System.out.println("Feedback added successfully.");
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
            System.out.println("\n=================================");
            System.out.println("         Add Menu Item");
            System.out.println("=================================");
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

            System.out.print("Enter Meal Type (BREAKFAST/LUNCH/DINNER/BEVERAGE): ");
            String mealType = scanner.nextLine().toUpperCase();

            MenuItem menuItem = new MenuItem(0, name, price, availability, menuDate, mealType);
            menuService.addMenuItem(menuItem);
            Notification notification = new Notification(0, user.getUserId(), "Menu Item added: " + name, new java.sql.Timestamp(System.currentTimeMillis()), false, "NEW_ITEM");
            notificationService.addNotificationForAllEmployees(notification);
            System.out.println("Menu Item added successfully.");
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
            System.out.println("\n=================================");
            System.out.println("       Update Menu Item");
            System.out.println("=================================");
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

            System.out.print("Enter Meal Type (BREAKFAST/LUNCH/DINNER/BEVERAGE): ");
            String mealType = scanner.nextLine().toUpperCase();

            MenuItem menuItem = new MenuItem(menuItemId, name, price, availability, menuDate, mealType);
            menuService.updateMenuItem(menuItem);
            Notification notification = new Notification(0, user.getUserId(), "Menu Item updated: " + name, new java.sql.Timestamp(System.currentTimeMillis()), false, "AVAILABILITY_CHANGE");
            notificationService.addNotificationForAllEmployees(notification);
            System.out.println("Menu Item updated successfully.");
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
            System.out.println("\n=================================");
            System.out.println("       Delete Menu Item");
            System.out.println("=================================");
            System.out.print("Enter Menu Item ID to delete: ");
            int menuItemId = scanner.nextInt();
            menuService.deleteMenuItem(menuItemId);
            Notification notification = new Notification(0, user.getUserId(), "Menu Item deleted: " + menuItemId, new java.sql.Timestamp(System.currentTimeMillis()), false, "AVAILABILITY_CHANGE");
            notificationService.addNotificationForAllEmployees(notification);
            System.out.println("Menu Item deleted successfully.");
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
            System.out.println("\n=================================");
            System.out.println("         Add User");
            System.out.println("=================================");
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
            System.out.println("User added successfully.");
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

            System.out.println("\n=================================");
            System.out.println("  Roll Out Recommendations");
            System.out.println("=================================");
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
                System.out.print("Enter Meal Type (BREAKFAST/LUNCH/DINNER/BEVERAGE): ");
                String mealType = scanner.nextLine().toUpperCase();
                Recommendation recommendation = new Recommendation(0, menuItemId, user.getUserId(), recommendationDate, "", "", mealType);
                recommendationService.addRecommendation(recommendation);
                menuItemIds.add(menuItemId);
            }
            Notification notification = new Notification(0, user.getUserId(), "Recommendations added for the next day", new java.sql.Timestamp(System.currentTimeMillis()), false, "RECOMMENDATION");
            notificationService.addNotificationForAllEmployees(notification);
            System.out.println("Recommendations processed successfully.");
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
            System.out.println("\n=================================");
            System.out.println("      Monthly Feedback Report");
            System.out.println("=================================");
            displayFeedbacks(allFeedback);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while generating the feedback report. Please try again.");
            e.printStackTrace();
        }
    }

    private void displayMenuItems(List<MenuItem> menuItems) {
        System.out.printf("%-5s %-20s %-10s %-15s %-10s %-10s%n", "ID", "Name", "Price", "Availability", "Menu Date", "Meal Type");
        System.out.println("--------------------------------------------------------------------");
        for (MenuItem menuItem : menuItems) {
            System.out.printf("%-5d %-20s %-10.2f %-15s %-10s %-10s%n", menuItem.getMenuItemId(), menuItem.getName(), menuItem.getPrice(), menuItem.isAvailability(), menuItem.getMenuDate(), menuItem.getMealType());
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

    private void displayMenuItemsWithRatings(List<MenuItem> menuItems) {
        System.out.printf("%-5s %-20s %-10s %-15s %-10s %-10s %-10s%n", "ID", "Name", "Price", "Availability", "Menu Date", "Meal Type", "Avg Rating");
        System.out.println("--------------------------------------------------------------------------------");
        for (MenuItem menuItem : menuItems) {
            try {
                double averageRating = feedbackService.getAverageRating(menuItem.getMenuItemId());
                System.out.printf("%-5d %-20s %-10.2f %-15s %-10s %-10s %-10.2f%n", menuItem.getMenuItemId(), menuItem.getName(), menuItem.getPrice(), menuItem.isAvailability(), menuItem.getMenuDate(), menuItem.getMealType(), averageRating);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void viewTopRatedItems() {
        try {
            List<MenuItem> topRatedItems = recommendationService.getTopRatedItems();
            System.out.println("\n=================================");
            System.out.println("       Top Rated Items");
            System.out.println("=================================");
            displayMenuItemsWithRatings(topRatedItems);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("An error occurred while fetching top-rated items. Please try again.");
            e.printStackTrace();
        }
    }
}
