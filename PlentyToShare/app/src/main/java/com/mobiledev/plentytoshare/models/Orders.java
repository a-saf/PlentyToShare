package com.mobiledev.plentytoshare.models;

import android.text.format.Time;

import java.time.LocalTime;
import java.util.Date;

public class Orders {

    private String restaurantID;
    private int numOfServings;
    private Date date;
    private Date expiryDate;
    private String status;
    private LocalTime pickupTime;

    public Orders() {}

    public Orders(String restaurantID, int numOfServings, Date date, String status, LocalTime pickupTime, Date expiryDate) {
        this.restaurantID = restaurantID;
        this.numOfServings = numOfServings;
        this.date = date;
        this.status = status;
        this.pickupTime = pickupTime;
        this.expiryDate = expiryDate;
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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

}
