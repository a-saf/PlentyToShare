package com.mobiledev.plentytoshare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mobiledev.plentytoshare.R;
import com.mobiledev.plentytoshare.models.Orders;

import java.util.ArrayList;

public class RestaurantPosting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_posting);

        //Get Firebase Restaurant Details
    }

    public void postFood(View view) {
        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);
    }
}