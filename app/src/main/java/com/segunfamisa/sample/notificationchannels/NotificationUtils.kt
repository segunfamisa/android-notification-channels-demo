package com.segunfamisa.sample.notificationchannels

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.graphics.Color
import android.support.v4.app.NotificationCompat

class NotificationUtils(private val context: Context) {
    companion object {
        private const val PM_NOTIFICATION_ID = 10
        private const val ACTIVITY_NOTIFICATION_ID = 11

        private const val PRIVATE_MESSAGES_CHANNEL_ID = "private_messages"
        private const val NEW_ACTIVITY_CHANNEL_ID = "new_activity"
    }

    private val notificationManager by lazy {
        context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {
        createDefaultChannels()
    }

    fun showPMNotification(message: String) {
        val notification = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notification_small)
                .setContentTitle("New Private message")
                .setContentText(message)
                /**
                 * further customizations, like sound, light color, vibration pattern etc
                 */
                .setChannelId(PRIVATE_MESSAGES_CHANNEL_ID)
                .build()
        notificationManager.notify(PM_NOTIFICATION_ID, notification)
    }

    fun showNewActivityNotification(message: String) {
        val notification = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notification_small)
                .setContentTitle("New like on your post")
                .setContentText(message)
                /**
                 * further customizations
                 */
                .setChannelId(NEW_ACTIVITY_CHANNEL_ID)
                .build()
        notificationManager.notify(ACTIVITY_NOTIFICATION_ID, notification)
    }

    @SuppressLint("NewApi")
    fun deletePmNotificationChannel() {
        isAndroidO {
            notificationManager.deleteNotificationChannel(PRIVATE_MESSAGES_CHANNEL_ID)
        }
    }

    @SuppressLint("NewApi")
    fun deleteNewActivityNotificationChannel() {
        isAndroidO {
            notificationManager.deleteNotificationChannel(NEW_ACTIVITY_CHANNEL_ID)
        }
    }

    @SuppressLint("NewApi")
    fun getPmNotificationChannel(): NotificationChannel? {
        isAndroidO {
            return notificationManager.getNotificationChannel(PRIVATE_MESSAGES_CHANNEL_ID)
        }
        return null
    }

    @SuppressLint("NewApi")
    fun getNewActivityNotificationChannel(): NotificationChannel? {
        isAndroidO {
            return notificationManager.getNotificationChannel(NEW_ACTIVITY_CHANNEL_ID)
        }
        return null
    }

    @SuppressLint("NewApi")
    fun notificationBlocked(channel: NotificationChannel): Boolean {
        isAndroidO {
            return channel.importance == NotificationManager.IMPORTANCE_NONE
        }
        return notificationManager.areNotificationsEnabled() // fallback for pre-O devices
    }

    @SuppressLint("NewApi")
    private fun createDefaultChannels() {
        // create the channels
        isAndroidO {
            val privateMessagesChannel = NotificationChannel(
                    PRIVATE_MESSAGES_CHANNEL_ID,
                    context.getString(R.string.pm_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT)

            with(privateMessagesChannel) {
                lightColor = Color.RED
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 300, 400)
                description = "Notification category for private messages"
            }

            val newActivityChannel = NotificationChannel(NEW_ACTIVITY_CHANNEL_ID,
                    context.getString(R.string.new_activity_channel_name), NotificationManager.IMPORTANCE_DEFAULT)

            notificationManager.createNotificationChannel(privateMessagesChannel)
            notificationManager.createNotificationChannel(newActivityChannel)
        }
    }
}