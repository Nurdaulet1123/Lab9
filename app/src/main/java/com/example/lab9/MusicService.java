package com.example.lab9;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.app.Service;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MusicService extends Service {

    private static final String CHANNEL_ID = "MusicChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String songTitle = intent.getStringExtra("songTitle");

        // Проверка версии Android для запуска ForegroundService
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1, createNotification(songTitle));
        } else {
            startService(intent);
        }

        return START_NOT_STICKY;
    }

    private Notification createNotification(String songTitle) {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Now Playing")
                .setContentText(songTitle)
                .setSmallIcon(R.drawable.ic_music_note) // иконка должна быть в drawable
                .build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Music Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Останавливаем сервис при уничтожении
        stopForeground(true);  // Останавливаем ForegroundService
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
