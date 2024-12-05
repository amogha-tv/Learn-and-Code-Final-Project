package org.itt.controller;

import org.itt.model.MenuItem;
import org.itt.model.Recommendation;
import org.itt.service.RecommendationService;
import org.itt.service.OrderService;
import org.itt.utility.RecommendationEngine;

import java.sql.SQLException;
import java.util.List;

public class RecommendationController {
    private final RecommendationService recommendationService;
    private final RecommendationEngine recommendationEngine;
    private final OrderService orderService;

    public RecommendationController() {
        this.recommendationService = new RecommendationService();
        this.recommendationEngine = new RecommendationEngine();
        this.orderService = new OrderService();
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
