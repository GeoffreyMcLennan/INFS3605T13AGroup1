package com.example.infs3605t13agroup1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private CheckBox checkBoxAcidReflux;
    // Declare CheckBox variables for other health conditions

    private EditText editTextOtherMedicalIssues;
    private EditText editTextSignificantInjuries;
    private EditText editTextSurgeries;
    private EditText userName;

    private SharedPreferences sharedPreferences;
    private int[] checkboxIds = {
            R.id.checkBoxAcidReflux,
            R.id.checkBoxAlcoholAddiction,
            R.id.checkBoxDepression,
            R.id.checkBoxSleepDisorders,
            R.id.checkBoxObesity,
            R.id.checkBoxThyroidIssues,
            R.id.checkBoxArthritis,
            R.id.checkBoxAllergyProblems,
            R.id.checkBoxAnemia,
            R.id.checkBoxAsthma,
            R.id.checkBoxDiabetes,
            R.id.checkBoxHighBloodPressure,
            R.id.checkBoxHeartDisease,
            R.id.checkBoxAutoImmuneDisease
    };
    private CheckBox[] checkBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userName = findViewById(R.id.editTextUserName);

        if (sharedPreferences.contains("Name")){
            String name = sharedPreferences.getString("Name", "hello");
            userName.setText(name);
        }

        checkBoxes = new CheckBox[checkboxIds.length];
        for(int i = 0; i< checkboxIds.length; i++){
            checkBoxes[i] = findViewById(checkboxIds[i]);
        }

        for(int i=0; i<checkBoxes.length; i++){
            String checkBoxKey = "Checkbox"+i;
            checkBoxes[i].setChecked(sharedPreferences.getBoolean(checkBoxKey, false));
        }

        Button buttonSaveMedicalDetails = findViewById(R.id.saveButton);

        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);
        BottomNavigationHelper.setupBottomNavigation(bottomNavView, this);

        // Highlight the profile icon in the bottom navigation bar
        bottomNavView.getMenu().findItem(R.id.menu_profile).setChecked(true);
        buttonSaveMedicalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMedicalDetails();
            }
        });
    }

    private void saveMedicalDetails() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name", userName.getText().toString());

        for(int i=0; i<checkBoxes.length; i++){
            String checkboxKey = "Checkbox"+i;
            editor.putBoolean(checkboxKey, checkBoxes[i].isChecked());
        }
        editor.apply();
        Toast.makeText(this,"Changes Saved", Toast.LENGTH_SHORT).show();
    }
}
