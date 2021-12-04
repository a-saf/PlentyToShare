package com.mobiledev.plentytoshare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobiledev.plentytoshare.R;
import com.mobiledev.plentytoshare.models.Charity;
import com.mobiledev.plentytoshare.models.Restaurant;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Locale;

public class RestaurantProfile extends AppCompatActivity {

    String username;
    DatabaseReference ref;
    String address;
    String youtube;
    String phone;
    Button phoneView;
    Button addressView;
    YouTubePlayerView youTubePlayerView;
    String youtubeID;
    String id;
    int servings;
    String charityUsername;
    String date;
    String expiry;
    String status;
    String type;
    String pickup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_profile);

        //Defining Toolbar and Up navigation
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_restaurant_profile);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //Get username from intent
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        id = intent.getStringExtra("id");
        charityUsername = intent.getStringExtra("charityusername");
        servings = intent.getIntExtra("servings",0);
        date = intent.getStringExtra("date");
        expiry = intent.getStringExtra("expiry");
        status = intent.getStringExtra("status");
        type = intent.getStringExtra("type");
        pickup = intent.getStringExtra("pickup");

        //Set Variables
        phoneView = findViewById(R.id.restaurant_phone_number);
        addressView = findViewById(R.id.restaurant_address);
        youTubePlayerView = findViewById(R.id.profile_youtube);

        //Observer for youtube player
        getLifecycle().addObserver(youTubePlayerView);
        //Set Title
        ab.setTitle(username.toUpperCase(Locale.ROOT));
        //Get restaurant details
        getData();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                // go to previous screen when app icon in action bar is clicked
                Intent intent = new Intent(this, CharityOrderDetail.class);
                intent.putExtra("restaurantusername", username);
                intent.putExtra("id", id);
                intent.putExtra("charityusername", charityUsername);
                intent.putExtra("servings", servings);
                intent.putExtra("date", date);
                intent.putExtra("expiry", expiry);
                intent.putExtra("status", status);
                intent.putExtra("type", type);
                intent.putExtra("pickup", pickup);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getData(){
        //Gets the Restaurants profile from firebase table
        ref = FirebaseDatabase.getInstance().getReference("restaurants");
        Query query = ref.orderByChild("username").equalTo(username);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Restaurant res = new Restaurant();
                    res = snapshot.getValue(Restaurant.class);
                    String pulledUsername = res.getUsername();
                    //Sets the address, phone and youtube variables which the users can interact with
                    if(username.equals(pulledUsername)){
                        address = res.getAddress();
                        phone = res.getPhoneNumber();
                        youtube = res.getYoutube();
                        displayData();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("CANCELLED:", "OnCancelled", databaseError.toException());
            }
        });

    }

    public void displayData(){
        //Sets the data which is retrieved from firebase to the buttons
        phoneView.setText(new StringBuilder().append(phoneView.getText().toString()).append("\n").append(phone).toString());
        addressView.setText(new StringBuilder().append(addressView.getText().toString()).append("\n").append(address).toString());
        youtubeID = youtube.split("=")[1];
        //Populates the youtube video player with the restaurant's profile
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(youtubeID, 0);
                super.onReady(youTubePlayer);
            }
        });
    }

    public void callNumber(View view) {
        //Creates a dialer intent which lets user click on the button and open the dialer with the phone number
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        System.out.println(phone);
        startActivity(intent);
    }

    public void navigateTo(View view) {
        //Creates an intent which lets users click on the button and open google maps navigation to the restaurant's address
        String geoUri = "http://maps.google.com/maps?q=loc:" + address;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
        startActivity(intent);
    }
}