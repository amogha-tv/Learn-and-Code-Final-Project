package org.itt.utility;

import org.itt.model.Recommendation;
import org.itt.model.Feedback;
import org.itt.service.FeedbackService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendationEngine {
    private final FeedbackService feedbackService;

    public RecommendationEngine() {
        this.feedbackService = new FeedbackService();
    }

    public List<Recommendation> getFilteredRecommendations(List<Recommendation> recommendations) throws SQLException, ClassNotFoundException {
        List<Recommendation> filteredRecommendations = new ArrayList<>();
        Map<Integer, List<Feedback>> feedbackByItem = new HashMap<>();

        for (Feedback feedback : feedbackService.getAllFeedback()) {
            feedbackByItem.computeIfAbsent(feedback.getMenuItemId(), k -> new ArrayList<>()).add(feedback);
        }

        for (Recommendation recommendation : recommendations) {
            List<Feedback> feedbacks = feedbackByItem.get(recommendation.getMenuItemId());
            if (feedbacks == null || feedbacks.isEmpty()) {
                continue;
            }

            double averageRating = feedbacks.stream().mapToInt(Feedback::getRating).average().orElse(0);
            long positiveSentiments = feedbacks.stream()
                    .filter(feedback -> "positive".equals(SentimentAnalysis.analyzeSentiment(feedback.getComment())))
                    .count();

            if (averageRating > 3 && positiveSentiments >= (feedbacks.size() / 2)) {
                filteredRecommendations.add(recommendation);
            }
        }

        return filteredRecommendations;
    }
}
