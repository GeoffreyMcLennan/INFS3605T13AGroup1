package com.example.infs3605t13agroup1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;
    private String selectedService;
    private String address, locality, displayLatitude, displayLongitude;
    private TextView location;
    private ImageButton locationButton;
    private static int PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ImageButton policeButton = findViewById(R.id.policeButton);
        ImageButton fireButton = findViewById(R.id.fireButton);
        ImageButton sesButton = findViewById(R.id.SESButton);
        ImageButton ambulanceButton = findViewById(R.id.ambulanceButton);
        ImageButton infoButton = findViewById(R.id.infoButton);
        ImageButton sosButton = findViewById(R.id.sosButton);
        location = findViewById(R.id.location);
        locationButton = findViewById(R.id.locationButton);

        final Animation pulseAnimation = AnimationUtils.loadAnimation(this, R.anim.button_pulse);

        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
        BottomNavigationHelper.setupBottomNavigation(bottomNavView, this);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
        }
        getCurrentLocation();

        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(pulseAnimation);
                Intent intent = new Intent(Intent.ACTION_CALL);
                String phoneNumber = "99999";
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });

        policeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(pulseAnimation);
                showEmergencyDialog("The Police");
                selectedService = "Police";
            }
        });

        fireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(pulseAnimation);
                showEmergencyDialog("The Fire Brigade");
                selectedService = "Fire and Rescue";
            }
        });

        sesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(pulseAnimation);
                showEmergencyDialog("The State Emergency Service");
                selectedService = "SES";
            }
        });

        ambulanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(pulseAnimation);
                showEmergencyDialog("The Ambulance");
                selectedService = "Ambulance";
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            String message = "If you know the nature of your emergency, choose Ambulance, Police, Fire, or State Emergency Service."
                    + "If you're unsure about your emergency, press the SOS button to connect with a Triple Zero (000) operator."
                    + "Only call if it's a genuine emergency.";

            @Override
            public void onClick(View view) {
                view.startAnimation(pulseAnimation);
                showInfoDialog("Information", message);
            }
        });
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(pulseAnimation);
                getCurrentLocation();
                showInfoDialog("Location", "Address: " + address + "\nLatitude: "
                        + displayLatitude + "\nLongitude: " + displayLongitude);
            }
        });
    }

    private void showEmergencyDialog(String serviceName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogue_custom_layout, null);

        // Set custom view with phone and message icons
        builder.setView(dialogView);

        TextView serviceNameTextView = dialogView.findViewById(R.id.serviceNameTextView);
        serviceNameTextView.setText("Contact " + serviceName);

        ImageButton phoneIconButton = dialogView.findViewById(R.id.phoneIconButton);
        ImageButton messageIconButton = dialogView.findViewById(R.id.messageIconButton);

        phoneIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement call functionality here
                Intent intent = new Intent(Intent.ACTION_CALL);
                String phoneNumber = "99999";
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });

        messageIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement text functionality here
                Intent intent = new Intent(MainActivity.this, TextActivity.class);
                intent.putExtra("service", serviceName);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle cancel button click here, if needed
                dialog.dismiss(); // Dismiss the dialog
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showInfoDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss the dialog when OK is clicked (optional)
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(MainActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(MainActivity.this).removeLocationUpdates(this);
                if (locationResult != null && locationResult.getLocations().size() > 0) {
                    int latestLocationIndex = locationResult.getLocations().size() - 1;
                    double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    Log.d("testing", Double.toString(latitude));
                    Geocoder geocoder;
                    geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    try {
                        List<Address> addressList;
                        addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        displayLatitude = Double.toString(latitude);
                        displayLongitude = Double.toString(longitude);
                        address = (addressList.get(0).getAddressLine(0));
                        locality = addressList.get(0).getLocality();
                        location.setText(locality);

                        Log.d("testing", address);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, Looper.getMainLooper());

    }
}
