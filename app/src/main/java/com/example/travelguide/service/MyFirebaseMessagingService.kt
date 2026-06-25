package com.example.travelguide.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import android.app.Application
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("FCM", "Message received")

        message.notification?.let {

            Log.d("FCM", "Title: ${it.title}")
            Log.d("FCM", "Body: ${it.body}")
        }




    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                "travel_channel",
                "Travel Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )

            val manager = getSystemService(
                NotificationManager::class.java
            )

            manager.createNotificationChannel(channel)
        }
    }
/*
    private fun showNotification(
        title: String,
        body: String
    ) {

        createNotificationChannel()

        val notification = NotificationCompat.Builder(
            this,
            "travel_channel"
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this)
            .notify(
                System.currentTimeMillis().toInt(),
                notification
            )
    }

 */

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("FCM_TOKEN", token)
    }
}