package com.example.lab9;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    Context context;
    ArrayList<Song> songList;

    public SongAdapter(Context context, ArrayList<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_item, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.txtTitle.setText(song.getTitle());
        holder.txtArtist.setText(song.getArtist());

        holder.itemView.setOnClickListener(view -> showPlayDialog(song));
    }

    private void showPlayDialog(Song song) {
        new AlertDialog.Builder(context)
                .setTitle("Play Song")
                .setMessage("Do you want to play \"" + song.getTitle() + "\" by " + song.getArtist() + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Intent to start MusicService
                    Intent serviceIntent = new Intent(context, MusicService.class);
                    serviceIntent.putExtra("songTitle", song.getTitle() + " - " + song.getArtist());

                    // Start service depending on Android version
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(serviceIntent);  // For Android 8.0 and above
                    } else {
                        context.startService(serviceIntent);  // For Android below 8.0
                    }

                    dialog.dismiss();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtArtist;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtArtist = itemView.findViewById(R.id.txtArtist);
        }
    }
}
