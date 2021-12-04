package com.mobiledev.plentytoshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    String id;
    public TextView view_order_id, view_servings, view_date, view_expiry, view_status, view_pickup, view_type;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_detailed_order);

        //Defining Toolbar and Up navigation
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_restaurant_detail);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //Firebase referece
        ref = FirebaseDatabase.getInstance().getReference("orders");

        //Instantiate the global variables
        view_order_id = findViewById(R.id.detail_orderid);
        view_servings = findViewById(R.id.detail_servings);
        view_date = findViewById(R.id.detail_date);
        view_expiry = findViewById(R.id.detail_expiry);
        view_status = findViewById(R.id.detail_status);
        view_pickup = findViewById(R.id.detail_pickup);
        view_type = findViewById(R.id.detail_type);

        //Unpack the state information coming in from the previous activity
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        username = intent.getStringExtra("username");

        //Set the values of the field to the order details
        view_order_id.setText("ID: " + String.valueOf(id));
        view_servings.setText("Servings: " + String.valueOf(intent.getIntExtra("servings", 0)));
        view_date.setText("Placed: " + intent.getStringExtra("date"));
        view_expiry.setText("Expires: " + intent.getStringExtra("expiry"));
        view_status.setText("Status: " + intent.getStringExtra("status"));
        String pickup = intent.getStringExtra("pickup");
        if(pickup == null){
            pickup = "Pending";
        }
        view_pickup.setText("Pickup: " + pickup);
        view_type.setText("Food Type: " + intent.getStringExtra("type"));

        Button deleteButton = findViewById(R.id.delete_btn);
        //Delete an available food posting and navigate back to the previous activity
        deleteButton.setOnClickListener(view -> {
            deleteRecord(id);
            Intent i = new Intent(this, RestaurantPosting.class);
            i.putExtra("username", username);

            startActivity(i);
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Used to override the default up button functionality and add an intent to it
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

    void deleteRecord(String id){
        //Iterates over the Firebase database and deletes the order which has the matching ID
        Query deleteQuery = ref.orderByChild("orderID").equalTo(id);
        deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot orderSnapshot: dataSnapshot.getChildren()){
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
