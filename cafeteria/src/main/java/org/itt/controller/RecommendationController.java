package org.itt.controller;

import org.itt.model.MenuItem;
import org.itt.model.Recommendation;
import org.itt.service.RecommendationService;

import java.sql.SQLException;
import java.util.List;

public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController() {
        this.recommendationService = new RecommendationService();
    }

    public void clearRecommendations() throws SQLException, ClassNotFoundException {
        recommendationService.clearRecommendations();
    }

    public void addRecommendation(int menuItemId, int userId, java.sql.Date recommendationDate, String mealType) throws SQLException, ClassNotFoundException {
        Recommendation recommendation = new Recommendation(0, menuItemId, userId, recommendationDate, "", "", mealType);
        recommendationService.addRecommendation(recommendation);
    }

    public List<Recommendation> getAllRecommendations() throws SQLException, ClassNotFoundException {
        return recommendationService.getAllRecommendations();
    }

    public List<MenuItem> getTopRatedItems() throws SQLException, ClassNotFoundException {
        return recommendationService.getTopRatedItems();
    }
}
