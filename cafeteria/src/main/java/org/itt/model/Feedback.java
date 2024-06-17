package org.itt.model;

import java.io.Serializable;
import java.sql.Date;

public class Feedback implements Serializable {
    private int feedbackId;
    private int userId;
    private int menuItemId;
    private String comment;
    private int rating;
    private Date dateOfFeedback;
    private String itemName;

    public Feedback(int feedbackId, int userId, int menuItemId, String comment, int rating, Date dateOfFeedback) {
        this.feedbackId = feedbackId;
        this.userId = userId;
        this.menuItemId = menuItemId;
        this.comment = comment;
        this.rating = rating;
        this.dateOfFeedback = dateOfFeedback;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDateOfFeedback() {
        return dateOfFeedback;
    }

    public void setDateOfFeedback(Date dateOfFeedback) {
        this.dateOfFeedback = dateOfFeedback;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
