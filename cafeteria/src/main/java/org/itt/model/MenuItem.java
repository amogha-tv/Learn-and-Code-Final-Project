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

    public MenuItem(int menuItemId, String name, BigDecimal price, boolean availability, Date menuDate, String mealType) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.price = price;
        this.availability = availability;
        this.menuDate = menuDate;
        this.mealType = mealType;
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

    @Override
    public String toString() {
        return "MenuItem{" +
                "menuItemId=" + menuItemId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                ", menuDate=" + menuDate +
                ", mealType='" + mealType + '\'' +
                '}';
    }
}
