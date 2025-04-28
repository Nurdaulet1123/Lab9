package com.example.lab9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnSettings;
    ArrayList<Song> songList;
    SongAdapter songAdapter;
    private static final String FILE_NAME = "songs.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSettings = findViewById(R.id.btnSettings);
        recyclerView = findViewById(R.id.recyclerView);

        songList = new ArrayList<>();
        loadSongs(); // Загружаем песни из файла или по умолчанию

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
        setTitle("Favorite: " + artist);
    }

    private void loadSongs() {
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    songList.add(new Song(parts[0], parts[1]));
                }
            }

            reader.close();
            fis.close();

            if (songList.isEmpty()) {
                loadDefaultSongs();
                saveSongs(); // сохраняем дефолтные песни в файл
            }

        } catch (Exception e) {
            // Файл не найден - загружаем дефолтные песни
            loadDefaultSongs();
            saveSongs();
        }
    }

    private void loadDefaultSongs() {
        songList.clear();
        songList.add(new Song("Song 1", "Artist A"));
        songList.add(new Song("Song 2", "Artist B"));
        songList.add(new Song("Song 3", "Artist C"));
    }

    private void saveSongs() {
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

            for (Song song : songList) {
                writer.write(song.getTitle() + ";" + song.getArtist());
                writer.newLine();
            }

            writer.flush();
            writer.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
