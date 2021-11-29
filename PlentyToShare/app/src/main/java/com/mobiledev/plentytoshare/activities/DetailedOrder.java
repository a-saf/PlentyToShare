package com.mobiledev.plentytoshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobiledev.plentytoshare.R;
import com.mobiledev.plentytoshare.models.Orders;

public class DetailedOrder extends AppCompatActivity {
    DatabaseReference ref;
    int id;
    public TextView view_order_id, view_servings, view_date, view_expiry, view_status, view_pickup, view_type;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_detailed_order);

        ref = FirebaseDatabase.getInstance().getReference("orders");

        view_order_id = findViewById(R.id.detail_orderid);

        view_servings = findViewById(R.id.detail_servings);
        view_date = findViewById(R.id.detail_date);
        view_expiry = findViewById(R.id.detail_expiry);
        view_status = findViewById(R.id.detail_status);
        view_pickup = findViewById(R.id.detail_pickup);
        view_type = findViewById(R.id.detail_type);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        username = intent.getStringExtra("username");


        view_order_id.setText("ID: " + String.valueOf(id));

        view_servings.setText("Servings" + String.valueOf(intent.getIntExtra("servings", 0)));
        view_date.setText("Placed: " + intent.getStringExtra("date"));
        view_expiry.setText("Expires: " + intent.getStringExtra("expiry"));
        view_status.setText("Status: " + intent.getStringExtra("status"));
        String pickup = intent.getStringExtra("pickup");
        if(pickup == null){
            pickup = "Pending";
        }
        view_pickup.setText("Pickup: " + pickup);
        view_type.setText("Food Type: " + intent.getStringExtra("type"));

        FloatingActionButton deleteButton = findViewById(R.id.detail_delete_record_button);
        deleteButton.setOnClickListener(view -> {
            deleteRecord(id);
            Intent i = new Intent(this, RestaurantPosting.class);
            i.putExtra("username", username);
            startActivity(i);
        });

    }

    void deleteRecord(int id){

        Query deleteQuery = ref.orderByChild("orderID").equalTo(id);
        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot orderSnapshot: dataSnapshot.getChildren()){
//                    Orders order = new Orders();
//                    order = orderSnapshot.getValue(Orders.class);
                    //System.out.println(order.getOrderID());
                    orderSnapshot.getRef().removeValue();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CANCELLED", "onCancelled", databaseError.toException());

            }
        });

    }
}
