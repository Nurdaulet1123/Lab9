package com.example.lab9;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Button btnSavePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnSavePrefs = findViewById(R.id.btnSavePrefs);

        btnSavePrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Здесь позже добавим SharedPreferences
                finish();
            }
        });
    }
}
