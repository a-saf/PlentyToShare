package com.mobiledev.plentytoshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobiledev.plentytoshare.R;
import com.mobiledev.plentytoshare.models.Charity;
import com.mobiledev.plentytoshare.models.Orders;
import com.mobiledev.plentytoshare.models.Restaurant;

public class LoginActivity extends AppCompatActivity {
    RadioButton ch, res;
    TextInputEditText username, password;
    DatabaseReference ref;
    String usernameValue, passwordValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ch = findViewById(R.id.radio_button_charity);
        res = findViewById(R.id.radio_button_restaurant);
        this.username = findViewById(R.id.input_username);
        this.password = findViewById(R.id.input_password);





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
                usernameValue = username.getText().toString();
                passwordValue = password.getText().toString();

                if(res.isChecked()){
                    ref = FirebaseDatabase.getInstance().getReference("restaurants");
                    Query query = ref.orderByChild("username").equalTo(usernameValue);

                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                                Restaurant res = new Restaurant();
                                res = snapshot.getValue(Restaurant.class);

                                String pulledUsername = res.getUsername();

                                String pulledPassword = res.getPassword();
                                if(usernameValue.equals(pulledUsername)){
                                    if(passwordValue.equals(pulledPassword)){
                                        Intent intent = new Intent(getApplicationContext(), RestaurantPosting.class);
                                        intent.putExtra("username", pulledUsername);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Invalid Login Credentials", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w("CANCELLED:", "OnCancelled", databaseError.toException());
                        }
                    });
                }
                else if(ch.isChecked()){
                    System.out.println("imhere");
                    ref = FirebaseDatabase.getInstance().getReference("charities");
                    Query query = ref.orderByChild("username").equalTo(usernameValue);

                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                                Charity cha = new Charity();
                                cha = snapshot.getValue(Charity.class);

                                String pulledUsername = cha.getUsername();

                                String pulledPassword = cha.getPassword();
                                if(usernameValue.equals(pulledUsername)){
                                    if(passwordValue.equals(pulledPassword)){
                                        Intent intent = new Intent(getApplicationContext(), CharityPostings.class);
                                        intent.putExtra("username", pulledUsername);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Invalid Login Credentials", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w("CANCELLED:", "OnCancelled", databaseError.toException());
                        }
                    });
                }



            }
        });


    }

}
