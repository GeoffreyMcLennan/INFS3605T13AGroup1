package com.example.infs3605t13agroup1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;
    private String selectedService;
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

        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
        BottomNavigationHelper.setupBottomNavigation(bottomNavView, this);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CODE);
        }

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
            @Override
            public void onClick(View view) {
                showInfoDialog();
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
    private void showInfoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Information");
        builder.setMessage("If you do not know the nature of your emergency, please click the SOS button " +
                "to automatically call a Triple Zero (000) operator. Select the Ambulance, Police, Fire " +
                "or State Emergency Service button if you do know the nature of your emergency. " +
                "Only call emergency services if it is a genuine emergency.");
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
}