package com.example.infs3605t13agroup1;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private CheckBox checkBoxAcidReflux;
    // Declare CheckBox variables for other health conditions

    private EditText editTextOtherMedicalIssues;
    private EditText editTextSignificantInjuries;
    private EditText editTextSurgeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_profile);

        checkBoxAcidReflux = findViewById(R.id.checkBoxAcidReflux);
        // Initialize CheckBox variables for other health conditions

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
        StringBuilder selectedHealthConditions = new StringBuilder();
        // Check which health conditions are selected
        if (checkBoxAcidReflux.isChecked()) {
            selectedHealthConditions.append("Acid Reflux, ");
        }
        // Check other health conditions similarly

        String otherMedicalIssues = editTextOtherMedicalIssues.getText().toString();
        String significantInjuries = editTextSignificantInjuries.getText().toString();
        String surgeries = editTextSurgeries.getText().toString();

        // Combine selected health conditions and user input into a single string
        String medicalDetails = selectedHealthConditions.toString() + otherMedicalIssues + "\n" +
                "Significant Injuries: " + significantInjuries + "\n" +
                "Surgeries/Procedures: " + surgeries;

        // Save or use the 'medicalDetails' string as needed, such as storing it in a database.
    }
}
