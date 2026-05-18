package com.weappsinc.screenpet

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weappsinc.screenpet.feature.home.presentation.nav.MainScaffold
import com.weappsinc.screenpet.feature.onboarding.presentation.FirstRunLanguageRoute
import com.weappsinc.screenpet.feature.onboarding.presentation.OnboardingRoute
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
    appEntryViewModel: AppEntryViewModel,
) {
    var showSplash by remember { mutableStateOf(!AppLocaleTransitionGate.isActive()) }
    val postSplashDestination by appEntryViewModel.postSplashDestination.collectAsStateWithLifecycle()
    val onboardingSeen by appEntryViewModel.onboardingSeen.collectAsStateWithLifecycle()
    var localDestination by remember { mutableStateOf<AppEntryDestination?>(null) }

    LaunchedEffect(showSplash, postSplashDestination) {
        if (!showSplash && localDestination == null && postSplashDestination != null) {
            localDestination = postSplashDestination
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            showSplash -> SplashRoute(
                viewModel = splashViewModel,
                onFinished = { showSplash = false },
            )
            localDestination == null -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
            )
            localDestination == AppEntryDestination.Language -> FirstRunLanguageRoute(
                activity = activity,
                onFinish = {
                    localDestination = if (onboardingSeen == true) {
                        AppEntryDestination.Main
                    } else {
                        AppEntryDestination.Onboarding
                    }
                },
            )
            localDestination == AppEntryDestination.Onboarding -> OnboardingRoute(
                onFinish = { localDestination = AppEntryDestination.Main },
            )
            else -> MainScaffold(
                devMenuContent = {
                    MainPetTestContent(activity = activity, petViewModel = petViewModel)
                },
            )
        }
        LocaleTransitionOverlay()
    }
}
