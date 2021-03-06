package com.mobiledev.plentytoshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    TextInputEditText showYouTube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_registration);

        //Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_restaurant_register);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //Sets the global variables
        this.showUsername = findViewById(R.id.text_username);
        this.showPassword = findViewById(R.id.text_password);
        this.showPhoneNumber = findViewById(R.id.text_phone_number);
        this.showFoodType = findViewById(R.id.text_food_type);
        this.showRestaurantName = findViewById(R.id.text_restaurant_name);
        this.showRegistrationId = findViewById(R.id.text_registration_id);
        this.showAddress = findViewById(R.id.text_location);
        this.showYouTube = findViewById(R.id.text_video_profile);
        //Firebase reference object
        ref = FirebaseDatabase.getInstance().getReference().child("restaurants");

    }


    public void createAccount(View view) {
        //Creates a new restaurant object using the input values by the user
        Restaurant res = new Restaurant(showUsername.getText().toString(),
                                        showPassword.getText().toString(),
                                        showPhoneNumber.getText().toString(),
                                        showFoodType.getText().toString(),
                                        showRestaurantName.getText().toString(),
                                        showRegistrationId.getText().toString(),
                                        showAddress.getText().toString(),
                                        showYouTube.getText().toString());

        //Add Restaurant object under Firebase
        ref.child(res.username).setValue(res);

        //Navigate to activity where they can post food sending their state
        Intent intent = new Intent(this, RestaurantPosting.class);
        intent.putExtra("username", this.showUsername.getText().toString());
        intent.putExtra("phone", this.showPhoneNumber.getText().toString());
        intent.putExtra("foodType", this.showFoodType.getText().toString());
        intent.putExtra("name", this.showRestaurantName.getText().toString());
        intent.putExtra("address", this.showAddress.getText().toString());
        startActivity(intent);
    }

}
