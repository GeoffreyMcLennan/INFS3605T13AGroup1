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
                int itemId = item.getItemId();
                if (itemId == R.id.menu_settings && !(activity instanceof SettingsActivity)) {
                    activity.startActivity(new Intent(activity, SettingsActivity.class));
                    return true;
                } else if (itemId == R.id.menu_home && !(activity instanceof MainActivity)) {
                    activity.startActivity(new Intent(activity, MainActivity.class));
                    return true;
                } else if (itemId == R.id.menu_profile && !(activity instanceof ProfileActivity)) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class));
                    bottomNavigationView.getMenu().findItem(R.id.menu_profile).setChecked(true); // Highlight Profile button
                    return true;
                }
                return false;
            }
        });
    }
}
