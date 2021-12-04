package com.mobiledev.plentytoshare.models;

public class Orders {

    private String orderID;
    private String username;
    private int numOfServings;
    private String date;
    private String expiryDate;
    private String status;
    private String pickupTime;
    private String foodType;
    private String address;

    public Orders() {}

    //Food available order model and the associated getters and setters
    public Orders(String orderID, String username, int numOfServings, String date, String expiryDate, String status, String pickupTime, String foodType, String address) {
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

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
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
