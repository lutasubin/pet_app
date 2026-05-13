package com.weappsinc.screenpet.feature.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.screenpet.feature.onboarding.domain.usecase.MarkOnboardingSeenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

/**
 * ViewModel cho onboarding: chi giu trach nhiem ghi co "da xem".
 * Page index do Compose `rememberPagerState` quan ly (UI state).
 */
@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val markOnboardingSeen: MarkOnboardingSeenUseCase,
) : ViewModel() {

    fun finishOnboarding(onPersisted: () -> Unit) {
        viewModelScope.launch {
            markOnboardingSeen()
            onPersisted()
        }
    }
}
