package com.example.infs3605t13agroup1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//To do - make question list for each emergency service
public class TextActivity extends AppCompatActivity {
    private RecyclerView messageRecyclerView;
    private EditText messageEditText;
    private Button sendButton;
    private boolean locationQuestion;
    private MessageAdapter messageAdapter;
    private ArrayList<Message> messageArrayList;
    private String currentTime;
    private String address;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    int LOCATION_PERMISSION_REQUEST = 1;
    private String[] predefinedResponses = {"Can you describe the emergency", "Stay safe!", "Emergency services have been alerted."};
    private int questionCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getIntent().getStringExtra("service"));
        locationQuestion = true;

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");

        messageRecyclerView = findViewById(R.id.messageListView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        messageArrayList = new ArrayList<>();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TextActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        messageAdapter = new MessageAdapter(TextActivity.this, messageArrayList);
        messageRecyclerView.setAdapter(messageAdapter);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(TextActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        } else {
            getCurrentLocation();
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleMessageSent();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(TextActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(TextActivity.this).removeLocationUpdates(this);
                if (locationResult != null && locationResult.getLocations().size() > 0) {
                    int latestLocationIndex = locationResult.getLocations().size() - 1;
                    double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    Log.d("testing", Double.toString(latitude));
                    Geocoder geocoder;
                    geocoder = new Geocoder(TextActivity.this, Locale.getDefault());
                    try {
                        List<Address> addressList;
                        addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        address = (addressList.get(0).getAddressLine(0));
                        createMessage("Is this your location: "+address, false);
                        Log.d("testing", address);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, Looper.getMainLooper());

    }

    public void handleMessageSent(){
        String enteredMessage = messageEditText.getText().toString();
        messageEditText.setText(null);
        if (enteredMessage.isEmpty()){
            Toast.makeText(getApplicationContext(),"Enter Message First", Toast.LENGTH_SHORT).show();
        }
        else if (locationQuestion == true){
            if(enteredMessage.equalsIgnoreCase("Yes")){
                createMessage(enteredMessage, true);
                locationQuestion = false;
                createMessage("Stay there help is on the way", false);
                createMessage(predefinedResponses[questionCount], false);
                questionCount ++;
            }
            else{
                createMessage(enteredMessage, true);
                locationQuestion = false;
                createMessage("What is your location", false);
            }
        }
        else{
            createMessage(enteredMessage, true);
            if (questionCount>predefinedResponses.length-1){
                questionCount = 1;
            }
            createMessage(predefinedResponses[questionCount], false);
            questionCount ++;
        }
    }
    public void createMessage(String enteredMessage, boolean isReceived){
        Date date = new Date();
        currentTime=simpleDateFormat.format(calendar.getTime());
        Message message = new Message (enteredMessage, currentTime, date.getTime(), isReceived);
        messageArrayList.add(message);
        messageAdapter.notifyDataSetChanged();
    }

}