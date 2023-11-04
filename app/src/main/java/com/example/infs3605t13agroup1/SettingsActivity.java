package com.example.infs3605t13agroup1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String IS_DARK_MODE = "isDarkMode";

    private Switch darkModeSwitch;
    private SharedPreferences preferences;
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

        // Spinner for language selection
        Spinner languageSpinner = findViewById(R.id.languageSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.languages_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle language selection logic here
                String selectedLanguage = parentView.getItemAtPosition(position).toString();
                // Set the selected language for the app
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        // Switch for enabling/disabling notifications
        Switch notificationSwitch = findViewById(R.id.notificationSwitch);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle notification enable/disable logic here
                if (isChecked) {
                    // Enable notifications
                } else {
                    // Disable notifications
                }
            }
        });

        // CheckBox for enabling accessibility features
        CheckBox accessibilityCheckBox = findViewById(R.id.accessibilityCheckBox);
        accessibilityCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle accessibility feature enable/disable logic here
                if (isChecked) {
                    // Enable accessibility features
                } else {
                    // Disable accessibility features
                }
            }
        });

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        darkModeSwitch = findViewById(R.id.darkModeSwitch);
        boolean isDarkModeEnabled = preferences.getBoolean(IS_DARK_MODE, false);
        darkModeSwitch.setChecked(isDarkModeEnabled);

        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save the state of the switch to SharedPreferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(IS_DARK_MODE, isChecked);
                editor.apply();

                // Apply dark mode based on the switch state
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

                // Restart the activity to apply the theme changes
                recreate();
            }
        });
    }
}

