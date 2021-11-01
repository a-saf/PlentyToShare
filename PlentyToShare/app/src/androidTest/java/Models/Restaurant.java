package Models;

public class Restaurant {

    public String username;
    public String password;
    public String phoneNumber;
    public String foodType;
    public String restaurantName;
    public String registrationID;
    public String address;

    public Restaurant() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Restaurant(String username, String password, String phoneNumber, String foodType, String restaurantName, String registrationID, String address) {

        this.username = username;
        this.password = password;
        this.phoneNumber=phoneNumber;
        this.foodType=foodType;
        this.restaurantName=restaurantName;
        this.registrationID=registrationID;
        this.address=address;

    }
}
