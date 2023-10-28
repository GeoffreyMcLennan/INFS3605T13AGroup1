package com.example.infs3605t13agroup1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutManager = new LinearLayoutManager(this);

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

    }
}