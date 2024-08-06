package org.itt.service;

import org.itt.dao.ProfileDAO;
import org.itt.model.Profile;

import java.sql.SQLException;

public class ProfileService {
    private final ProfileDAO profileDAO;

    public ProfileService() {
        this.profileDAO = new ProfileDAO();
    }

    public void addOrUpdateProfile(Profile profile) throws SQLException, ClassNotFoundException {
        profileDAO.addOrUpdateProfile(profile);
    }

    public Profile getProfileByUserId(int userId) throws SQLException, ClassNotFoundException {
        return profileDAO.getProfileByUserId(userId);
    }
}
