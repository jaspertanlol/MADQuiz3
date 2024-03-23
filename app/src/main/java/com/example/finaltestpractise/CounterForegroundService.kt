package com.example.finaltestpractise

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.*

class CounterForegroundService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private var count = 1

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel() // Create the notification channel
        val notification = createNotification("Counter Running")
        startForeground(1, notification)
        serviceScope.launch {
            while (isActive) {
                println("Counter: $count")
                count++
                delay(1000) // Delay for 1 second
                if (count > 5) {
                    stopSelf()
                }
            }
        }
        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        val name = "Foreground Service Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("channel_id", name, importance)
        val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(contentText: String): Notification {
        val notificationBuilder = Notification.Builder(this, "channel_id").apply {
            setContentTitle("Foreground Service")
            setContentText(contentText)
            setSmallIcon(R.drawable.ic_launcher_background)
        }
        return notificationBuilder.build()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
