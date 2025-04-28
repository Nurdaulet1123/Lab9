package com.example.lab9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnSettings;
    ArrayList<Song> songList;
    SongAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSettings = findViewById(R.id.btnSettings);
        recyclerView = findViewById(R.id.recyclerView);

        songList = new ArrayList<>();
        loadSongs(); // Загружаем песни

        songAdapter = new SongAdapter(this, songList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(songAdapter);

        btnSettings.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavoriteArtist();
    }

    private void loadFavoriteArtist() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String artist = preferences.getString("favorite_artist", "Unknown Artist");
        setTitle("Favorite: " + artist); // Установим заголовок активности
    }

    private void loadSongs() {
        songList.add(new Song("Song 1", "Artist A"));
        songList.add(new Song("Song 2", "Artist B"));
        songList.add(new Song("Song 3", "Artist C"));
    }
}
