package com.mobiledev.plentytoshare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobiledev.plentytoshare.R;
import com.mobiledev.plentytoshare.adapters.RVAdapterRestaurant;
import com.mobiledev.plentytoshare.models.Charity;
import com.mobiledev.plentytoshare.models.Orders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CharityPostings extends AppCompatActivity implements RVAdapterRestaurant.OrderViewHolder.OnOrderListener {
    private ArrayList<Orders> orderList = new ArrayList<>();
    private RecyclerView orderRecyclerView;
    DatabaseReference dbOrder;
    Geocoder coder;
    String username;
    String address;
    double latitude;
    double longitude;
    String addressFound;


    RVAdapterRestaurant orderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_posting);

        //Create a Geocoder instance
        coder = new Geocoder(this);

        //Defining Toolbar and Up navigation
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_charity_view);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //Get the state from the previous activity
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        address = "";

        //Get Address and Lat and Long of the Charity to calculate distace
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("charities");
        Query query = ref.orderByChild("username").equalTo(username);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Charity res = new Charity();
                    res = snapshot.getValue(Charity.class);
                    String pulledUsername = res.getUsername();
                    if(username.equals(pulledUsername)){
                        addressFound = res.getAddress();
                        getAddress(addressFound);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("CANCELLED:", "OnCancelled", databaseError.toException());
            }
        });
    }

    public void getAddress(String address){
        //Uses geocoder to get a LatLng object from an address
        LatLng charityLatLong = getLatLongFromAddress(address);
        latitude = charityLatLong.latitude;
        longitude = charityLatLong.longitude;
        firebaseRecyclerView();
    }

    public void firebaseRecyclerView(){
        //Starts the process of populating the Recyclerview from the orders table
        dbOrder = FirebaseDatabase.getInstance().getReference("orders");
        orderRecyclerView = findViewById(R.id.restaurant_recycler_view);
        displayOrders();
    }

    private void displayView(){
        //Uses the RVAdapter to populate the Recyclerview with a linear layout
        orderAdapter = new RVAdapterRestaurant(orderList, this, username);
        orderRecyclerView.setAdapter(orderAdapter);
        orderRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
    }


    private void displayOrders(){
        //Routing method to order how population should occur
        populateOrderList();
        displayView();
    }

    private void populateOrderList(){
        //ArrayList object which will hold all the orders available
        orderList = new ArrayList<>();
        // Used to iterate over the firebase data in and retreive values
       dbOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    //For every entry in the firebase db create a new Order object
                    Orders order = new Orders();
                    order = snapshot.getValue(Orders.class);
                    //Get the order object's address and use that to get the Lat, Long pair
                    LatLng restaurantLatLng = getLatLongFromAddress(order.getAddress());
                    double restaurantLatitude = restaurantLatLng.latitude;
                    double restaurantLongitude = restaurantLatLng.longitude;

                    float[] results = new float[1];
                    //Calculates the distance between the Charity and Restaurant in the order
                    Location.distanceBetween(latitude, longitude , restaurantLatitude, restaurantLongitude, results);
                    //Only display the orders in a 20Km radius of the charity
                    if(results[0]<20000){
                        orderList.add(order);
                    }
                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("CANCELLED:", "OnCancelled", databaseError.toException());
            }
        });


    }

    public LatLng getLatLongFromAddress(String strAddress){
        List<Address> address;
        LatLng p1 = null;
        //Use geocoder to create a LatLng object
        try {
            address = coder.getFromLocationName(strAddress,2);
            if (address == null){
                return null;
            }
            else {
                Address location  = address.get(0);
                p1 = new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return p1;
    }

    //Take user to new activity where they can view details about the order
    //video will also be played here
    //will be able to click on restaurant's profile to view their information, as well as video
    @Override
    public void onOrderClick(int position) {

        Intent intent = new Intent(this, CharityOrderDetail.class);
        intent.putExtra("id", orderList.get(position).getOrderID());
        intent.putExtra("charityusername", username);
        intent.putExtra("address", address);
        intent.putExtra("restaurantusername", orderList.get(position).getUsername());
        intent.putExtra("servings", orderList.get(position).getNumOfServings());
        intent.putExtra("date", orderList.get(position).getDate());
        intent.putExtra("expiry", orderList.get(position).getExpiryDate());
        intent.putExtra("status", orderList.get(position).getStatus());
        intent.putExtra("pickup", orderList.get(position).getPickupTime());
        intent.putExtra("type", orderList.get(position).getFoodType());
        startActivity(intent);
    }
}