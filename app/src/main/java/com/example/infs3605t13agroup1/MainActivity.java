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
import android.view.View;
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
        location = findViewById(R.id.location);
        locationButton = findViewById(R.id.locationButton);

        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
        BottomNavigationHelper.setupBottomNavigation(bottomNavView, this);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
        }
        getCurrentLocation();

        policeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmergencyDialog("the Police");
                selectedService = "Police";
            }
        });

        fireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmergencyDialog("the Fire Brigade");
                selectedService = "Fire and Rescue";
            }
        });

        sesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmergencyDialog("the State Emergency Service");
                selectedService = "SES";
            }
        });
        ambulanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmergencyDialog("the Ambulance");
                selectedService = "Ambulance";
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            String message = "If you do not know the nature of your emergency, please click the SOS button " +
                    "to automatically call a Triple Zero (000) operator. Select the Ambulance, Police, Fire " +
                    "or State Emergency Service button if you do know the nature of your emergency. " +
                    "Only call emergency services if it is a genuine emergency.";

            @Override
            public void onClick(View view) {
                showInfoDialog("Information", message);
            }
        });
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
                showInfoDialog("Location", "Address: " + address + "\nLatitude: "
                        + displayLatitude + "\nLongitude: " + displayLongitude);
            }
        });
    }

    private void showEmergencyDialog(String serviceName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Contact " + serviceName);
        builder.setMessage("Do you want to call or text " + serviceName + "?");
        builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Implement call functionality here
                Intent intent = new Intent(Intent.ACTION_CALL);
                String phoneNumber = "99999";
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Text", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Implement text functionality here
                Intent intent = new Intent(MainActivity.this, TextActivity.class);
                intent.putExtra("service", selectedService);
                startActivity(intent);
            }
        });
        builder.show();
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
