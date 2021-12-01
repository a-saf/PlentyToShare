package com.mobiledev.plentytoshare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobiledev.plentytoshare.R;
import com.mobiledev.plentytoshare.adapters.RVAdapterRestaurant;
import com.mobiledev.plentytoshare.models.Orders;

import java.io.IOException;
import java.lang.reflect.Array;
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


    RVAdapterRestaurant orderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_posting);

        coder = new Geocoder(this);

        //Defining Toolbar and Up navigation
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_charity_view);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        address = intent.getStringExtra("address");
        LatLng charityLatLong = getLatLongFromAddress(address);
        latitude = charityLatLong.latitude;
        longitude = charityLatLong.longitude;


        //Get Firebase Restaurant Details


        dbOrder = FirebaseDatabase.getInstance().getReference("orders");

        orderRecyclerView = findViewById(R.id.restaurant_recycler_view);
        displayOrders();





    }
    private void displayView(){
        orderAdapter = new RVAdapterRestaurant(orderList, this);
        orderRecyclerView.setAdapter(orderAdapter);
        orderRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
    }


    private void displayOrders(){
        populateOrderList();
        displayView();
    }
    private void populateOrderList(){
        orderList = new ArrayList<>();



       dbOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){



                    Orders order = new Orders();
                    order = snapshot.getValue(Orders.class);
                    LatLng restaurantLatLng = getLatLongFromAddress(order.getAddress());
                    double restaurantLatitude = restaurantLatLng.latitude;
                    double restaurantLongitude = restaurantLatLng.longitude;

                    float[] results = new float[1];
                    Location.distanceBetween(latitude, longitude , restaurantLatitude, restaurantLongitude, results);

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
        intent.putExtra("username", orderList.get(position).getUsername());
        intent.putExtra("servings", orderList.get(position).getNumOfServings());
        intent.putExtra("date", orderList.get(position).getDate());
        intent.putExtra("expiry", orderList.get(position).getExpiryDate());
        intent.putExtra("status", orderList.get(position).getStatus());
        intent.putExtra("pickup", orderList.get(position).getPickupTime());
        intent.putExtra("type", orderList.get(position).getFoodType());
        startActivity(intent);



    }
}