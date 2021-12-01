package com.mobiledev.plentytoshare.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobiledev.plentytoshare.R;
import com.mobiledev.plentytoshare.models.Charity;
import com.mobiledev.plentytoshare.models.Restaurant;

public class CharitiesRegistrationActivity extends AppCompatActivity {

    DatabaseReference ref;
    TextInputEditText showUsername;
    TextInputEditText showPassword;
    TextInputEditText showPhoneNumber;
    TextInputEditText showCharityName;
    TextInputEditText showRegistrationId;
    TextInputEditText showAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charities_registration);

        this.showUsername = findViewById(R.id.text_username);
        this.showPassword = findViewById(R.id.text_password);
        this.showPhoneNumber = findViewById(R.id.text_phone_number);
        this.showCharityName = findViewById(R.id.text_charity_name);
        this.showRegistrationId = findViewById(R.id.text_registration_id);
        this.showAddress = findViewById(R.id.text_location);

        ref = FirebaseDatabase.getInstance().getReference().child("charities");

    }

    public void createAccount(View view) {
        //Create validators for missing data, existing data
        Charity charity = new Charity(showUsername.getText().toString(),
                showPassword.getText().toString(),
                showPhoneNumber.getText().toString(),
                showCharityName.getText().toString(),
                showRegistrationId.getText().toString(),
                showAddress.getText().toString());

        //Add new charity object into Firebase
        ref.child(charity.username).setValue(charity);
        //Navigate to Page where Charities can see food available
        Intent intent = new Intent(this, CharityPostings.class);
        intent.putExtra("username", this.showUsername.getText().toString());
        intent.putExtra("name", this.showCharityName.getText().toString());
        intent.putExtra("phone", this.showPhoneNumber.getText().toString());
        intent.putExtra("address", this.showAddress.getText().toString());
        startActivity(intent);
    }
}