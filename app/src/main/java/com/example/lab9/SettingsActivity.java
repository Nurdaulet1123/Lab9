package com.example.lab9;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Button btnSavePrefs;
    EditText edtFavoriteArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnSavePrefs = findViewById(R.id.btnSavePrefs);
        edtFavoriteArtist = findViewById(R.id.edtFavoriteArtist);

        btnSavePrefs.setOnClickListener(view -> {
            String favoriteArtist = edtFavoriteArtist.getText().toString();

            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("favorite_artist", favoriteArtist);
            editor.apply(); // сохраняем

            finish(); // Возвращаемся на главный экран
        });

        loadPreferences();
    }

    private void loadPreferences() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String artist = preferences.getString("favorite_artist", "");
        edtFavoriteArtist.setText(artist);
    }
}
