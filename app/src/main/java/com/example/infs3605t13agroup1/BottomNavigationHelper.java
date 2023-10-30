package com.example.infs3605t13agroup1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

public class BottomNavigationHelper {

    public static void setupBottomNavigation(BottomNavigationView bottomNavigationView, final AppCompatActivity activity) {
        bottomNavigationView.setSelectedItemId(R.id.menu_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_settings) {
                    activity.startActivity(new Intent(activity, SettingsActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.menu_home) {
                    // Handle home action
                    return true;
                } else if (item.getItemId() == R.id.menu_profile) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}
