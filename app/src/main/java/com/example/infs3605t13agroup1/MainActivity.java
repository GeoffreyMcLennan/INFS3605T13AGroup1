package com.example.infs3605t13agroup1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutManager = new LinearLayoutManager(this);
        ImageButton policeButton = findViewById(R.id.policeButton);
        ImageButton fireButton = findViewById(R.id.fireButton);
        ImageButton sesButton = findViewById(R.id.SESButton);
        ImageButton ambulanceButton = findViewById(R.id.ambulanceButton);

        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
        final Context context = this; // Store MainActivity.this in a final variable
        bottomNavView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_settings) {
                startActivity(new Intent(context, SettingsActivity.class));
                return true;
            } else if (item.getItemId() == R.id.menu_home) {
                startActivity(new Intent(context, MainActivity.class));
                return true;
            } else if (item.getItemId() == R.id.menu_profile) {
                startActivity(new Intent(context, ProfileActivity.class));
                return true;
            } else {
                return false;
            }
        });

        policeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmergencyDialog("the Police");
            }
        });

        fireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmergencyDialog("the Fire Brigade");
            }
        });

        sesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmergencyDialog("the State Emergency Service");
            }
        });

        ambulanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmergencyDialog("the Ambulance");
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
            }
        });
        builder.setNegativeButton("Text", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Implement text functionality here
            }
        });
        builder.show();
    }


}