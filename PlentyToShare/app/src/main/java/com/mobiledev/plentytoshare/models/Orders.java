package com.mobiledev.plentytoshare.models;

import java.time.LocalTime;
import java.util.Date;

public class Orders {

    private String orderID;
    private String username;
    private int numOfServings;
    private String date;
    private String expiryDate;
    private String status;
    private LocalTime pickupTime;
    private String foodType;
    private String address;

    public Orders() {}

    public Orders(String orderID, String username, int numOfServings, String date, String expiryDate, String status, LocalTime pickupTime, String foodType, String address) {
        this.orderID = orderID;
        this.username = username;
        this.numOfServings = numOfServings;
        this.date = date;
        this.expiryDate = expiryDate;
        this.status = status;
        this.pickupTime = pickupTime;
        this.foodType = foodType;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumOfServings() {
        return numOfServings;
    }

    public void setNumOfServings(int numOfServings) {
        this.numOfServings = numOfServings;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
}
