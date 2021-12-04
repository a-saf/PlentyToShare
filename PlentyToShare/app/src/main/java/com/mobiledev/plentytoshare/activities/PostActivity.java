package com.mobiledev.plentytoshare.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobiledev.plentytoshare.R;
import com.mobiledev.plentytoshare.models.Orders;
import com.mobiledev.plentytoshare.models.Restaurant;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class PostActivity extends AppCompatActivity {

    DatabaseReference dbOrder;
    MaterialDatePicker datePicker;
    TextInputEditText foodType;
    TextInputEditText numServing;
    String expiryDate;
    String foodTypeValue;
    int servingsValue;
    DatabaseReference ref;

    int existingOrders;
    String orderDate;
    String status;
    LocalTime pickupTime;
    String username;
    String key;
    String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //Defining Toolbar and Up navigation
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //Instantiate global variables
        foodType = findViewById(R.id.text_food_available);
        numServing = findViewById(R.id.text_food_servings);
        dbOrder = FirebaseDatabase.getInstance().getReference().child("orders"); //Firebase database reference
        key = dbOrder.push().getKey(); //Get a unique order id from firebase to save the order into
        System.out.println(key);


        //Get the state data from the incoming intent
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        //Used to iterate over the firebase restaurant table to get the address of the restaurant which
        //is required to create a new order
        ref = FirebaseDatabase.getInstance().getReference("restaurants");
        Query query = ref.orderByChild("username").equalTo(username);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Restaurant res = new Restaurant();
                    res = snapshot.getValue(Restaurant.class);
                    String pulledUsername = res.getUsername();
                    if(username.equals(pulledUsername)){
                        address = res.getAddress();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("CANCELLED:", "OnCancelled", databaseError.toException());
            }
        });


        //Setting up Material Date Picker object
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Food Expiry Date");
        datePicker = builder.build();

        //Date Picker onclick handler
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                TimeZone timeZoneUTC = TimeZone.getDefault();
                // It will be negative, so that's the -1
                int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
                // Create a date format, then a date object with our offset
                SimpleDateFormat simpleFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                Date date = new Date(selection + offsetFromUTC);
                expiryDate = simpleFormat.format(date);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // go to previous screen when app icon in action bar is clicked
                Intent intent = new Intent(this, RestaurantPosting.class);
                intent.putExtra("username", username);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void expiryDate(View view) {
        //Shows the material design date picker to pick the expiry
        datePicker.show(getSupportFragmentManager(), "EXPIRY_DATE_PICKER");
    }

    public void createAvailable(View view) {
        //Get the values of the food type and number of servings
        foodTypeValue = foodType.getText().toString();
        servingsValue = Integer.parseInt(numServing.getText().toString());

        //Today's Date
        orderDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        String status = "Available";

        //Create a new Order and push it to firebase
        String orderID = key;
        Orders order = new Orders(orderID,username,servingsValue,orderDate,expiryDate,status,null,foodTypeValue, address);
        dbOrder.child(String.valueOf(orderID)).setValue(order);

        //Navigate back to the previous activity once posted with the state
        Intent intent = new Intent(this, RestaurantPosting.class);
        intent.putExtra("username", username);
        startActivity(intent);

    }
}