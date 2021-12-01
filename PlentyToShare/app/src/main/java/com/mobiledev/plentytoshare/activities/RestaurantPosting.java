package com.mobiledev.plentytoshare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RestaurantPosting extends AppCompatActivity implements RVAdapterRestaurant.OrderViewHolder.OnOrderListener {
    private ArrayList<Orders> orderList = new ArrayList<>();
    private RecyclerView orderRecyclerView;
    DatabaseReference dbOrder;
    String username;
    RVAdapterRestaurant orderAdapter;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_posting);

        //Defining Toolbar and Up navigation
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_restaurant_recycler);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //Get Firebase Restaurant Details
        Intent intent = getIntent();

        username = intent.getStringExtra("username");
        System.out.println("USERNAME: " + username);
        dbOrder = FirebaseDatabase.getInstance().getReference("orders");


        orderRecyclerView = findViewById(R.id.restaurant_recycler_view);
        displayOrders();


    }
    private void displayView(){
        orderAdapter = new RVAdapterRestaurant(orderList, this, username);
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

        Query query = dbOrder.orderByChild("username").equalTo(username);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Orders order = new Orders();
                    order = snapshot.getValue(Orders.class);
                    orderList.add(order);

                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("CANCELLED:", "OnCancelled", databaseError.toException());
            }
        });

    }

    public void postFood(View view) {
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    @Override
    public void onOrderClick(int position) {
        Intent intent = new Intent(this, DetailedOrder.class);
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