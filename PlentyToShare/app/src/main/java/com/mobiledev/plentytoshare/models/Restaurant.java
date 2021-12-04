package com.mobiledev.plentytoshare.models;

public class Restaurant {
    public String username;
    public String password;
    public String phoneNumber;
    public String foodType;
    public String restaurantName;
    public String registrationID;
    public String address;
    public String youtube;

    public Restaurant() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    //Restaurant model and the associated getter and setter
    public Restaurant(String username, String password, String phoneNumber, String foodType, String restaurantName, String registrationID, String address, String youtube) {

        this.username = username;
        this.password = password;
        this.phoneNumber=phoneNumber;
        this.foodType=foodType;
        this.restaurantName=restaurantName;
        this.registrationID=registrationID;
        this.address=address;
        this.youtube = youtube;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }
}
