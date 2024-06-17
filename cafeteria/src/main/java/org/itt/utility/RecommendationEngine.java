package org.itt.utility;

import org.itt.model.Feedback;
import org.itt.model.Recommendation;
import org.itt.service.FeedbackService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class RecommendationEngine {
    private final FeedbackService feedbackService;

    public RecommendationEngine() {
        this.feedbackService = new FeedbackService();
    }

    public boolean shouldRecommend(int menuItemId) throws SQLException, ClassNotFoundException {
        List<Feedback> feedbacks = feedbackService.getFeedbackForMenuItem(menuItemId);
        if (feedbacks.isEmpty()) {
            System.out.println("No feedback available for MenuItemID: " + menuItemId);
            return false;
        }

        double averageRating = feedbacks.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);

        return averageRating > 3;
    }

    public List<Recommendation> getFilteredRecommendations(List<Recommendation> recommendations) throws SQLException, ClassNotFoundException {
        return recommendations.stream()
                .filter(recommendation -> {
                    try {
                        return shouldRecommend(recommendation.getMenuItemId());
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }
}
