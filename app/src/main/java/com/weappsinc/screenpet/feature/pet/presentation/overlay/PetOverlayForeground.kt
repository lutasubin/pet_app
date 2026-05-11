package com.weappsinc.screenpet.feature.pet.presentation.overlay

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.core.constants.PetOverlayContract

object PetOverlayForeground {

    fun ensureChannel(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val mgr = context.getSystemService(NotificationManager::class.java)
        val ch = NotificationChannel(
            PetOverlayContract.CHANNEL_ID,
            context.getString(R.string.pet_overlay_channel_name),
            NotificationManager.IMPORTANCE_LOW,
        )
        mgr.createNotificationChannel(ch)
    }

    fun start(service: Service) {
        ensureChannel(service)
        val stopIntent = Intent(service, PetOverlayService::class.java).apply {
            action = PetOverlayContract.ACTION_STOP
        }
        val stopPi = PendingIntent.getService(
            service,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        val notification: Notification = NotificationCompat.Builder(service, PetOverlayContract.CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(service.getString(R.string.pet_overlay_notification_title))
            .setContentText(service.getString(R.string.pet_overlay_notification_body))
            .setOngoing(true)
            .addAction(0, service.getString(R.string.pet_overlay_stop), stopPi)
            .build()
        service.startForeground(
            PetOverlayContract.NOTIFICATION_ID,
            notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE,
        )
    }
}
