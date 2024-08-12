package org.itt.utility;

import org.itt.model.Recommendation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationEngineTest {
    private RecommendationEngine recommendationEngine;

    @BeforeEach
    void setUp() {
        recommendationEngine = new RecommendationEngine();
    }

    @Test
    void testGetFilteredRecommendations() {
        List<Recommendation> recommendations = new ArrayList<>();
        recommendations.add(new Recommendation(1, 1, 1, java.sql.Date.valueOf("2024-01-01"), "Item 1", "Recommender 1", "BREAKFAST"));
        recommendations.add(new Recommendation(2, 2, 1, java.sql.Date.valueOf("2024-01-01"), "Item 2", "Recommender 2", "BREAKFAST"));
        recommendations.add(new Recommendation(3, 3, 1, java.sql.Date.valueOf("2024-01-01"), "Item 3", "Recommender 3", "BREAKFAST"));

        List<Recommendation> filteredRecommendations = null;
        try {
            filteredRecommendations = recommendationEngine.getFilteredRecommendations(recommendations);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3, filteredRecommendations.size());
        assertTrue(filteredRecommendations.stream().allMatch(rec -> rec.getMealType().equals("BREAKFAST")));
    }
}
