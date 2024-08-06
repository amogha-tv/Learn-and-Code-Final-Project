package org.itt.model;

public class Profile {
    private int profileId;
    private int userId;
    private String foodPreference;
    private String spiceLevel;
    private String cuisinePreference;
    private boolean sweetTooth;

    public Profile() {
    }

    public Profile(int profileId, int userId, String foodPreference, String spiceLevel, String cuisinePreference, boolean sweetTooth) {
        this.profileId = profileId;
        this.userId = userId;
        this.foodPreference = foodPreference;
        this.spiceLevel = spiceLevel;
        this.cuisinePreference = cuisinePreference;
        this.sweetTooth = sweetTooth;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFoodPreference() {
        return foodPreference;
    }

    public void setFoodPreference(String foodPreference) {
        this.foodPreference = foodPreference;
    }

    public String getSpiceLevel() {
        return spiceLevel;
    }

    public void setSpiceLevel(String spiceLevel) {
        this.spiceLevel = spiceLevel;
    }

    public String getCuisinePreference() {
        return cuisinePreference;
    }

    public void setCuisinePreference(String cuisinePreference) {
        this.cuisinePreference = cuisinePreference;
    }

    public boolean isSweetTooth() {
        return sweetTooth;
    }

    public void setSweetTooth(boolean sweetTooth) {
        this.sweetTooth = sweetTooth;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profileId=" + profileId +
                ", userId=" + userId +
                ", foodPreference='" + foodPreference + '\'' +
                ", spiceLevel='" + spiceLevel + '\'' +
                ", cuisinePreference='" + cuisinePreference + '\'' +
                ", sweetTooth=" + sweetTooth +
                '}';
    }
}
