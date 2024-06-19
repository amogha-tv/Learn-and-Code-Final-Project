package org.itt.service;

import org.itt.model.MenuItem;
import org.itt.model.Recommendation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationServiceTest {
    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        recommendationService = new RecommendationService();
    }

    @Test
    void testAddRecommendation() throws SQLException, ClassNotFoundException {
        Recommendation recommendation = new Recommendation(0, 1, 1, java.sql.Date.valueOf("2024-01-01"), "", "", "BREAKFAST");
        recommendationService.addRecommendation(recommendation);
        List<Recommendation> recommendations = recommendationService.getAllRecommendations();
        assertTrue(recommendations.stream().anyMatch(rec -> rec.getMenuItemId() == 1));
    }

    @Test
    void testClearRecommendations() throws SQLException, ClassNotFoundException {
        recommendationService.clearRecommendations();
        List<Recommendation> recommendations = recommendationService.getAllRecommendations();
        assertTrue(recommendations.isEmpty());
    }

    @Test
    void testGetAllRecommendations() throws SQLException, ClassNotFoundException {
        List<Recommendation> recommendations = recommendationService.getAllRecommendations();
        assertNotNull(recommendations);
    }

    @Test
    void testGetTopRatedItems() throws SQLException, ClassNotFoundException {
        List<MenuItem> topRatedItems = recommendationService.getTopRatedItems();
        assertNotNull(topRatedItems);
    }
}
