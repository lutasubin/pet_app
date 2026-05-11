package com.weappsinc.screenpet

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.weappsinc.screenpet.feature.home.presentation.nav.MainScaffold
import com.weappsinc.screenpet.feature.pet.presentation.PetViewModel
import com.weappsinc.screenpet.feature.splash.presentation.SplashRoute
import com.weappsinc.screenpet.feature.splash.presentation.SplashViewModel

@Composable
fun AppEntryContent(
    activity: ComponentActivity,
    splashViewModel: SplashViewModel,
    petViewModel: PetViewModel,
) {
    var showSplash by remember { mutableStateOf(true) }
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
}
