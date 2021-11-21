package com.mobiledev.plentytoshare.models;

import android.text.format.Time;

import java.time.LocalTime;
import java.util.Date;

public class CalendarEvent {

    private String restaurantID;
    private int numOfServings;
    private Date date;
    private String status;
    private LocalTime pickupTime;

    public CalendarEvent() {}

    public CalendarEvent(String restaurantID, int numOfServings, Date date, String status, LocalTime pickupTime) {
        this.restaurantID = restaurantID;
        this.numOfServings = numOfServings;
        this.date = date;
        this.status = status;
        this.pickupTime = pickupTime;
    }

    public String getStatus() {
        return status;
    }

    public LocalTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public void setStatus(String status) {
        this.status = status;
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
