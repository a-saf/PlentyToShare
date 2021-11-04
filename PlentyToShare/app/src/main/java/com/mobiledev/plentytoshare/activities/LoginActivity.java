package com.mobiledev.plentytoshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mobiledev.plentytoshare.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Button newAccountBtn = findViewById(R.id.register_button);
        newAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChooseProfileTypeActivity.class);
                startActivity(intent);
            }
        });

        Button loginBtn = findViewById(R.id.signin_button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RestaurantCalendarActivity.class);
                startActivity(intent);
            }
        });


    }

}
