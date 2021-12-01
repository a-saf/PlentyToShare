package com.mobiledev.plentytoshare.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobiledev.plentytoshare.R;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class CharityOrderDetail extends AppCompatActivity{
    String restaurantUsername;
    String charityUsername;
    String id;
    int servings;
    String date;
    String expiry;
    String status;
    String pickup;
    String type;
    String pickupTimeSelected;
    DatabaseReference ref;

    MaterialTextView orderRestaurant;
    Spinner spinner;
    MaterialTextView orderID;
    MaterialTextView orderServings;
    MaterialTextView orderDate;
    MaterialTextView orderExpiry;
    MaterialTextView orderType;
    MaterialTextView orderStatus;
    MaterialTextView orderPickup;
    Button cancelBtn;
    Button acceptBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_order_detail);

        //Defining Toolbar and Up navigation
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_charity_detail);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //Instantiate global variables
        orderRestaurant = findViewById(R.id.detail_charity_restaurant_name);
        orderID = findViewById(R.id.detail_charity_orderid);
        orderServings = findViewById(R.id.detail_charity_servings);
        orderDate = findViewById(R.id.detail_charity_date);
        orderExpiry = findViewById(R.id.detail_charity_expiry);
        orderType = findViewById(R.id.detail_charity_type);
        orderStatus = findViewById(R.id.detail_charity_status);
        orderPickup = findViewById(R.id.detail_charity_pickup);
        cancelBtn = findViewById(R.id.cancel_charity_order_btn);
        acceptBtn = findViewById(R.id.accept_charity_order_btn);
        ref = FirebaseDatabase.getInstance().getReference().child("orders");

        //Get incoming data
        Intent intent = getIntent();
        restaurantUsername = intent.getStringExtra("restaurantusername");
        id = intent.getStringExtra("id");
        charityUsername = intent.getStringExtra("charityusername");
        servings = intent.getIntExtra("servings",0);
        date = intent.getStringExtra("date");
        expiry = intent.getStringExtra("expiry");
        status = intent.getStringExtra("status");
        type = intent.getStringExtra("type");
        pickup = intent.getStringExtra("pickup");

        //Update Fields with data
        orderRestaurant.setText(new StringBuilder().append(orderRestaurant.getText().toString()).append(": ").append(restaurantUsername).toString());
        orderID.setText(new StringBuilder().append(orderID.getText().toString()).append(": ").append(id).toString());
        orderServings.setText(new StringBuilder().append(orderServings.getText().toString()).append(": ").append(servings).toString());
        orderDate.setText(new StringBuilder().append(orderDate.getText().toString()).append(": ").append(date).toString());
        orderExpiry.setText(new StringBuilder().append(orderExpiry.getText().toString()).append(": ").append(expiry).toString());
        orderType.setText(new StringBuilder().append(orderType.getText().toString()).append(": ").append(type).toString());
        orderStatus.setText(new StringBuilder().append(orderStatus.getText().toString()).append(": ").append(status).toString());
        if (pickup != null){
            orderPickup.setText(new StringBuilder().append(orderPickup.getText().toString()).append(": ").append(pickup).toString());
        }

        //Toggle the order Buttons
        if (orderStatus.equals(charityUsername)){
            cancelBtn.setVisibility(View.VISIBLE);
            acceptBtn.setVisibility(View.INVISIBLE);

        }
        else{
            cancelBtn.setVisibility(View.INVISIBLE);
            acceptBtn.setVisibility(View.VISIBLE);
        }

        //Instantiate spinner
        spinner = findViewById(R.id.pickup_time_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pickup_time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_restaurant_profile:
                Intent intent = new Intent(this, RestaurantProfile.class);
                intent.putExtra("username", restaurantUsername);
                startActivity(intent);
                break;
            case android.R.id.home:
                // go to previous screen when app icon in action bar is clicked
                navigateBack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void acceptOrder(View view) {
        pickupTimeSelected = spinner.getSelectedItem().toString();
        ref.child(id).child("status").setValue(charityUsername);
        ref.child(id).child("pickupTime").setValue(pickupTimeSelected);
        navigateBack();
    }

    public void cancelOrder(View view) {
        ref.child(id).child("status").setValue("Available");
        ref.child(id).child("pickupTime").setValue(null);
        navigateBack();
    }

    public void navigateBack(){
        Intent intent = new Intent(this, CharityPostings.class);
        intent.putExtra("username", charityUsername);
        startActivity(intent);
    }
}