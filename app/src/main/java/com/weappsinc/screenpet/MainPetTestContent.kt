package com.weappsinc.screenpet

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.weappsinc.screenpet.feature.pet.presentation.PetTestScreen
import com.weappsinc.screenpet.feature.pet.presentation.PetViewModel
import com.weappsinc.screenpet.feature.pet.presentation.overlay.PetOverlayService

@Composable
fun MainPetTestContent(
    activity: ComponentActivity,
    petViewModel: PetViewModel,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var overlayAllowed by remember { mutableStateOf(Settings.canDrawOverlays(activity)) }
    DisposableEffect(lifecycleOwner) {
        val obs = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                overlayAllowed = Settings.canDrawOverlays(activity)
            }
        }
        lifecycleOwner.lifecycle.addObserver(obs)
        onDispose { lifecycleOwner.lifecycle.removeObserver(obs) }
    }

    val notifLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { granted ->
        if (granted) PetOverlayService.start(activity)
    }

    PetTestScreen(
        viewModel = petViewModel,
        canDrawOverlays = overlayAllowed,
        onOpenOverlaySettings = {
            activity.startActivity(
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:${activity.packageName}"),
                ),
            )
        },
        onStartOverlay = startOverlay@{
            if (!Settings.canDrawOverlays(activity)) {
                activity.startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:${activity.packageName}"),
                    ),
                )
                return@startOverlay
            }
            val okNotif = ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
            if (!okNotif) {
                notifLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                return@startOverlay
            }
            PetOverlayService.start(activity)
        },
        onStopOverlay = { PetOverlayService.stop(activity) },
    )
}
