package com.segunfamisa.sample.notificationchannels

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val privateMessageButton by lazy { findViewById<Button>(R.id.pm_notif_button) }
    private val newActivityButton by lazy { findViewById<Button>(R.id.new_activity_notify_button) }

    private val deleteMessageChannelButton by lazy { findViewById<Button>(R.id.delete_pm_channel_button) }
    private val deleteNewActivityChannelButton by lazy { findViewById<Button>(R.id.delete_new_activity_channel_button) }

    private val privateMessageNotifStatusTextView by lazy { findViewById<TextView>(R.id.pm_notif_channel_status) }
    private val newActivityNotifStatusTextView by lazy { findViewById<TextView>(R.id.new_activity_notif_channel_status) }

    private lateinit var notificationUtils: NotificationUtils

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationUtils = NotificationUtils(this)

        privateMessageButton.setOnClickListener {
            notificationUtils.showPMNotification("Hey, just received new PM from @user")
        }

        newActivityButton.setOnClickListener {
            notificationUtils.showNewActivityNotification("Hey @user just liked your post")
        }

        deleteMessageChannelButton.setOnClickListener {
            notificationUtils.deletePmNotificationChannel()
        }

        deleteNewActivityChannelButton.setOnClickListener {
            notificationUtils.deleteNewActivityNotificationChannel()
        }

        val pmNotificationChannel = notificationUtils.getPmNotificationChannel()
        val newActivityNotificationChannel = notificationUtils.getNewActivityNotificationChannel()

        isAndroidO {
            val pmNotificationsBlocked = notificationBlocked(pmNotificationChannel)
            privateMessageNotifStatusTextView.show(pmNotificationsBlocked)

            val newActivityNotificationsBlocked = notificationBlocked(newActivityNotificationChannel)
            newActivityNotifStatusTextView.show(newActivityNotificationsBlocked)
        }
    }

    private fun notificationBlocked(notificationChannel: NotificationChannel?): Boolean {
        notificationChannel?.let {
            return notificationUtils.notificationBlocked(notificationChannel)
        }
        return true
    }

    private fun View.show(show: Boolean = true) {
        visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
