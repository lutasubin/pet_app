package com.weappsinc.screenpet.feature.home.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

/**
 * Khi bat Kich hoat Shimeji: xin quyen overlay + thong bao truoc khi goi [onActivateEnabled].
 * Tat thi goi [onActivateDisabled] ngay.
 */
@Composable
fun rememberHomeActivatePermissionHandler(
    activity: ComponentActivity,
    onActivateEnabled: () -> Unit,
    onActivateDisabled: () -> Unit,
): (Boolean) -> Unit {
    var pendingEnable by remember { mutableStateOf(false) }
    val onEnabledUpdated by rememberUpdatedState(onActivateEnabled)
    val onDisabledUpdated by rememberUpdatedState(onActivateDisabled)
    val lifecycleOwner = LocalLifecycleOwner.current

    fun hasNotificationPermission(): Boolean =
        ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED

    fun tryCompleteEnable() {
        if (!pendingEnable) return
        if (!Settings.canDrawOverlays(activity)) return
        if (!hasNotificationPermission()) return
        onEnabledUpdated()
        pendingEnable = false
    }

    val notifLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { granted ->
        if (granted) tryCompleteEnable() else pendingEnable = false
    }

    DisposableEffect(lifecycleOwner) {
        val obs = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (pendingEnable && Settings.canDrawOverlays(activity) && hasNotificationPermission()) {
                    tryCompleteEnable()
                } else if (pendingEnable && Settings.canDrawOverlays(activity) && !hasNotificationPermission()) {
                    notifLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(obs)
        onDispose { lifecycleOwner.lifecycle.removeObserver(obs) }
    }

    return remember(activity, notifLauncher) {
        { enabled: Boolean ->
            when {
                !enabled -> {
                    pendingEnable = false
                    onDisabledUpdated()
                }
                !Settings.canDrawOverlays(activity) -> {
                    pendingEnable = true
                    activity.startActivity(
                        Intent(
                            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:${activity.packageName}"),
                        ),
                    )
                }
                !hasNotificationPermission() -> {
                    pendingEnable = true
                    notifLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                else -> {
                    pendingEnable = true
                    tryCompleteEnable()
                }
            }
        }
    }
}
