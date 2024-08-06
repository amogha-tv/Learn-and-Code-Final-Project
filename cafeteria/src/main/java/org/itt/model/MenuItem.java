package org.itt.model;

import java.math.BigDecimal;
import java.sql.Date;

public class MenuItem {
    private int menuItemId;
    private String name;
    private BigDecimal price;
    private boolean availability;
    private Date menuDate;
    private String mealType;
    private boolean isSweet;
    private boolean isSpicy;
    private String vegType;

    public MenuItem() {
    }

    public MenuItem(int menuItemId, String name, BigDecimal price, boolean availability, Date menuDate, String mealType, boolean isSweet, boolean isSpicy, String vegType) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.price = price;
        this.availability = availability;
        this.menuDate = menuDate;
        this.mealType = mealType;
        this.isSweet = isSweet;
        this.isSpicy = isSpicy;
        this.vegType = vegType;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public Date getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(Date menuDate) {
        this.menuDate = menuDate;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public boolean isSweet() {
        return isSweet;
    }

    public void setSweet(boolean sweet) {
        isSweet = sweet;
    }

    public boolean isSpicy() {
        return isSpicy;
    }

    public void setSpicy(boolean spicy) {
        isSpicy = spicy;
    }

    public String getVegType() {
        return vegType;
    }

    public void setVegType(String vegType) {
        this.vegType = vegType;
    }
}
