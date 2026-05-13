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
    // Neu vua recreate do doi locale -> bo qua splash de overlay loading chiem suot man.
    var showSplash by remember { mutableStateOf(!AppLocaleTransitionGate.isActive()) }
    val onboardingSeen by appEntryViewModel.onboardingSeen.collectAsStateWithLifecycle()
    // Trang thai onboarding chi quyet dinh khi flag da load + splash da xong.
    var showOnboarding by remember { mutableStateOf<Boolean?>(null) }
    LaunchedEffect(onboardingSeen, showSplash) {
        if (!showSplash && onboardingSeen != null && showOnboarding == null) {
            showOnboarding = onboardingSeen == false
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            showSplash -> SplashRoute(
                viewModel = splashViewModel,
                onFinished = { showSplash = false },
            )
            // Splash xong nhung flag chua kip load -> mau trang trang an toan, tranh nhay den.
            showOnboarding == null -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
            )
            showOnboarding == true -> OnboardingRoute(onFinish = { showOnboarding = false })
            else -> MainScaffold(
                devMenuContent = {
                    MainPetTestContent(activity = activity, petViewModel = petViewModel)
                },
            )
        }
        LocaleTransitionOverlay()
    }
}
