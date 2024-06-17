package org.itt.service;

import org.itt.dao.RecommendationDAO;
import org.itt.model.Recommendation;

import java.sql.SQLException;
import java.util.List;

public class RecommendationService {
    private final RecommendationDAO recommendationDAO;

    public RecommendationService() {
        this.recommendationDAO = new RecommendationDAO();
    }

    public List<Recommendation> getAllRecommendations() throws SQLException, ClassNotFoundException {
        return recommendationDAO.getAllRecommendations();
    }

    public void addRecommendation(Recommendation recommendation) throws SQLException, ClassNotFoundException {
        recommendationDAO.addRecommendation(recommendation);
    }
}
