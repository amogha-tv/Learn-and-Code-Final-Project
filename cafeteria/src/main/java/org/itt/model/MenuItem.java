package org.itt.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MenuItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private int menuItemId;
    private String name;
    private BigDecimal price;
    private boolean availability;
    private Date menuDate;

    public MenuItem() {
    }

    public MenuItem(int menuItemId, String name, BigDecimal price, boolean availability, Date menuDate) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.price = price;
        this.availability = availability;
        this.menuDate = menuDate;
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

    @Override
    public String toString() {
        return "MenuItem{" +
                "menuItemId=" + menuItemId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                ", menuDate=" + menuDate +
                '}';
    }
}
