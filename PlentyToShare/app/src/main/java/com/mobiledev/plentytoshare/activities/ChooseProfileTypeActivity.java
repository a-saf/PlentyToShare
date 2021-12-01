package com.mobiledev.plentytoshare.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mobiledev.plentytoshare.R;

import java.util.Objects;

public class ChooseProfileTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_profile_type);

        //Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_activity_choose);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Button restaurantBtn = findViewById(R.id.btn_restaurant);
        restaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RestaurantRegistrationActivity.class);
                startActivity(intent);
            }
        });

        Button charityBtn = findViewById(R.id.btn_charity);
        charityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CharitiesRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}