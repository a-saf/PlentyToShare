package com.mobiledev.plentytoshare.activities;

import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.mobiledev.plentytoshare.R;
import com.mobiledev.plentytoshare.models.Restaurant;


public class RestaurantRegistrationActivity extends AppCompatActivity {

    DatabaseReference ref;


    TextInputEditText showUsername;
    TextInputEditText showPassword;
    TextInputEditText showPhoneNumber;
    TextInputEditText showFoodType;
    TextInputEditText showRestaurantName;
    TextInputEditText showRegistrationId;
    TextInputEditText showAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_registration);

        this.showUsername = findViewById(R.id.text_username);
        this.showPassword = findViewById(R.id.text_password);
        this.showPhoneNumber = findViewById(R.id.text_phone_number);
        this.showFoodType = findViewById(R.id.text_food_type);
        this.showRestaurantName = findViewById(R.id.text_restaurant_name);
        this.showRegistrationId = findViewById(R.id.text_registration_id);
        this.showAddress = findViewById(R.id.text_location);

        ref = FirebaseDatabase.getInstance().getReference().child("restaurants");




    }


    public void createAccount(View view) {

        Restaurant res = new Restaurant(showUsername.getText().toString(),
                                        showPassword.getText().toString(),
                                        showPhoneNumber.getText().toString(),
                                        showFoodType.getText().toString(),
                                        showRestaurantName.getText().toString(),
                                        showRegistrationId.getText().toString(),
                                        showAddress.getText().toString());

        ref.child(res.username).setValue(res);



    }
}
