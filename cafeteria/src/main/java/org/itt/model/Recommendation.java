package org.itt.model;

import java.sql.Date;

public class Recommendation {
    private int recommendationId;
    private int menuItemId;
    private int recommendedBy;
    private Date recommendationDate;
    private String itemName;
    private String recommenderName;
    private String mealType;

    public Recommendation(int recommendationId, int menuItemId, int recommendedBy, Date recommendationDate, String itemName, String recommenderName, String mealType) {
        this.recommendationId = recommendationId;
        this.menuItemId = menuItemId;
        this.recommendedBy = recommendedBy;
        this.recommendationDate = recommendationDate;
        this.itemName = itemName;
        this.recommenderName = recommenderName;
        this.mealType = mealType;
    }

    public int getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(int recommendationId) {
        this.recommendationId = recommendationId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getRecommendedBy() {
        return recommendedBy;
    }

    public void setRecommendedBy(int recommendedBy) {
        this.recommendedBy = recommendedBy;
    }

    public Date getRecommendationDate() {
        return recommendationDate;
    }

    public void setRecommendationDate(Date recommendationDate) {
        this.recommendationDate = recommendationDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getRecommenderName() {
        return recommenderName;
    }

    public void setRecommenderName(String recommenderName) {
        this.recommenderName = recommenderName;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "recommendationId=" + recommendationId +
                ", menuItemId=" + menuItemId +
                ", recommendedBy=" + recommendedBy +
                ", recommendationDate=" + recommendationDate +
                ", itemName='" + itemName + '\'' +
                ", recommenderName='" + recommenderName + '\'' +
                ", mealType='" + mealType + '\'' +
                '}';
    }
}
