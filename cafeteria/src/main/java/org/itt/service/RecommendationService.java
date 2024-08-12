package org.itt.service;

import org.itt.dao.RecommendationDAO;
import org.itt.model.Recommendation;
import org.itt.model.MenuItem;
import org.itt.dao.FeedbackDAO;
import org.itt.dao.MenuItemDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class RecommendationService {
    private final RecommendationDAO recommendationDAO;
    private final FeedbackDAO feedbackDAO;
    private final MenuItemDAO menuItemDAO;

    public RecommendationService() {
        this.recommendationDAO = new RecommendationDAO();
        this.feedbackDAO = new FeedbackDAO();
        this.menuItemDAO = new MenuItemDAO();
    }

    public void addRecommendation(Recommendation recommendation) throws SQLException, ClassNotFoundException {
        recommendationDAO.addRecommendation(recommendation);
    }

    public void clearRecommendations() throws SQLException, ClassNotFoundException {
        recommendationDAO.clearRecommendations();
    }

    public List<Recommendation> getAllRecommendations() throws SQLException, ClassNotFoundException {
        return recommendationDAO.getAllRecommendations();
    }

    public List<MenuItem> getTopRatedItems() throws SQLException, ClassNotFoundException {
        List<MenuItem> allItems = menuItemDAO.getAllMenuItems();
        return allItems.stream()
                .filter(item -> {
                    try {
                        return feedbackDAO.getAverageRating(item.getMenuItemId()) > 3;
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }
}
