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
            int menuItemId = feedback.getMenuItemId();
            if (!feedbackByItem.containsKey(menuItemId)) {
                feedbackByItem.put(menuItemId, new ArrayList<Feedback>());
            }
            feedbackByItem.get(menuItemId).add(feedback);
        }

        for (Recommendation recommendation : recommendations) {
            int menuItemId = recommendation.getMenuItemId();
            List<Feedback> feedbacks = feedbackByItem.get(menuItemId);
            if (feedbacks == null || feedbacks.isEmpty()) {
                continue;
            }

            int totalRating = 0;
            for (Feedback feedback : feedbacks) {
                totalRating += feedback.getRating();
            }
            double averageRating = (double) totalRating / feedbacks.size();

            int positiveSentiments = 0;
            for (Feedback feedback : feedbacks) {
                if ("positive".equals(SentimentAnalysis.analyzeSentiment(feedback.getComment()))) {
                    positiveSentiments++;
                }
            }

            if (averageRating > 3 && positiveSentiments >= (feedbacks.size() / 2)) {
                filteredRecommendations.add(recommendation);
            }
        }

        return filteredRecommendations;
    }
}
