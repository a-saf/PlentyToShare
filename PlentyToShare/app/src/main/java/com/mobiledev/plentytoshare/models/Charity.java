package com.mobiledev.plentytoshare.models;

public class Charity {
    public String username;
    public String password;
    public String phoneNumber;
    public String charityName;
    public String registrationID;
    public String address;

    public Charity() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    //Charity model with the associated getters and setters
    public Charity(String username, String password, String phoneNumber, String charityName, String registrationID, String address) {

        this.username = username;
        this.password = password;
        this.phoneNumber=phoneNumber;
        this.charityName=charityName;
        this.registrationID=registrationID;
        this.address=address;
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

    public String getCharityName() {
        return charityName;
    }

    public void setCharityName(String charityName) {
        this.charityName = charityName;
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
}
