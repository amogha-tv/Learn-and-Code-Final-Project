package org.itt.dao;

import org.itt.model.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileDAO {
    public void addOrUpdateProfile(Profile profile) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "INSERT INTO Profile (userId, foodPreference, spiceLevel, cuisinePreference, sweetTooth) VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE foodPreference = VALUES(foodPreference), spiceLevel = VALUES(spiceLevel), cuisinePreference = VALUES(cuisinePreference), sweetTooth = VALUES(sweetTooth)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, profile.getUserId());
        statement.setString(2, profile.getFoodPreference());
        statement.setString(3, profile.getSpiceLevel());
        statement.setString(4, profile.getCuisinePreference());
        statement.setBoolean(5, profile.isSweetTooth());
        statement.executeUpdate();
        statement.close();
    }

    public Profile getProfileByUserId(int userId) throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnector.getInstance().getConnection();
        String query = "SELECT * FROM Profile WHERE userId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        Profile profile = null;
        if (resultSet.next()) {
            profile = new Profile();
            profile.setProfileId(resultSet.getInt("profileId"));
            profile.setUserId(resultSet.getInt("userId"));
            profile.setFoodPreference(resultSet.getString("foodPreference"));
            profile.setSpiceLevel(resultSet.getString("spiceLevel"));
            profile.setCuisinePreference(resultSet.getString("cuisinePreference"));
            profile.setSweetTooth(resultSet.getBoolean("sweetTooth"));
        }
        resultSet.close();
        statement.close();
        return profile;
    }
}
