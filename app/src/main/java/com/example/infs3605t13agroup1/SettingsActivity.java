package com.example.infs3605t13agroup1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getIntent().getStringExtra("service"));
        getSupportActionBar().hide();

        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
        BottomNavigationHelper.setupBottomNavigation(bottomNavView, this);

        // Highlight the profile icon in the bottom navigation bar
        bottomNavView.getMenu().findItem(R.id.menu_settings).setChecked(true);
    }

}
