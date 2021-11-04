package com.mobiledev.plentytoshare.models;

import java.util.Date;

public class CalendarEvent {

    private String restaurantID;
    private int numOfServings;
    private Date date;

    public CalendarEvent() {}

    public CalendarEvent(String restaurantID, int numOfServings, Date date) {
        this.restaurantID = restaurantID;
        this.numOfServings = numOfServings;
        this.date = date;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public int getNumOfServings() {
        return numOfServings;
    }

    public void setNumOfServings(int numOfServings) {
        this.numOfServings = numOfServings;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
