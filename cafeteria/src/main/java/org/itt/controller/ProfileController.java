package org.itt.controller;

import org.itt.model.Profile;
import org.itt.service.ProfileService;

import java.sql.SQLException;

public class ProfileController {
    private final ProfileService profileService;

    public ProfileController() {
        this.profileService = new ProfileService();
    }

    public void addOrUpdateProfile(Profile profile) throws SQLException, ClassNotFoundException {
        profileService.addOrUpdateProfile(profile);
    }

    public Profile getProfileByUserId(int userId) throws SQLException, ClassNotFoundException {
        return profileService.getProfileByUserId(userId);
    }
}
