package com.weappsinc.screenpet

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.weappsinc.screenpet.feature.home.presentation.nav.MainScaffold
import com.weappsinc.screenpet.feature.pet.presentation.PetViewModel
import com.weappsinc.screenpet.feature.settings.presentation.AppLocaleTransitionGate
import com.weappsinc.screenpet.feature.settings.presentation.LocaleTransitionOverlay
import com.weappsinc.screenpet.feature.splash.presentation.SplashRoute
import com.weappsinc.screenpet.feature.splash.presentation.SplashViewModel

@Composable
fun AppEntryContent(
    activity: ComponentActivity,
    splashViewModel: SplashViewModel,
    petViewModel: PetViewModel,
) {
    // Neu vua recreate do doi locale -> bo qua splash de overlay loading chiem suot man.
    var showSplash by remember { mutableStateOf(!AppLocaleTransitionGate.isActive()) }
    Box(modifier = Modifier.fillMaxSize()) {
        if (showSplash) {
            SplashRoute(
                viewModel = splashViewModel,
                onFinished = { showSplash = false },
            )
        } else {
            MainScaffold(
                devMenuContent = {
                    MainPetTestContent(activity = activity, petViewModel = petViewModel)
                },
            )
        }
        LocaleTransitionOverlay()
    }
}
