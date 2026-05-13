package com.weappsinc.screenpet.feature.onboarding.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Entry point cua onboarding: lay ViewModel, render screen.
 * Khi tap START o slide cuoi -> persist co "da xem" -> goi [onFinish].
 */
@Composable
fun OnboardingRoute(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    OnboardingScreen(
        onFinish = { viewModel.finishOnboarding(onPersisted = onFinish) },
        modifier = modifier,
    )
}
