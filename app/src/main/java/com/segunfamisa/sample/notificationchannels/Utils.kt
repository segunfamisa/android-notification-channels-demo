package com.segunfamisa.sample.notificationchannels

import android.os.Build

inline fun isAndroidO(behaviour: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        behaviour()
    }
}