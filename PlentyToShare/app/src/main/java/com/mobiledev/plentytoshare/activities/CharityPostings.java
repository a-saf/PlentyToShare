package com.mobiledev.plentytoshare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class CharityPostings extends AppCompatActivity implements RVAdapterRestaurant.OrderViewHolder.OnOrderListener {
    private ArrayList<Orders> orderList = new ArrayList<>();
    private RecyclerView orderRecyclerView;
    DatabaseReference dbOrder;
    String username;
    RVAdapterRestaurant orderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_posting);

        //Get Firebase Restaurant Details
        username = "osmows";
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

    //Take user to new activity where they can view details about the order
    //video will also be played here
    //will be able to click on restaurant's profile to view their information, as well as video
    @Override
    public void onOrderClick(int position) {




    }
}