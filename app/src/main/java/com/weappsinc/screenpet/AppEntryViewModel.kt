package com.weappsinc.screenpet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.screenpet.feature.onboarding.domain.usecase.ObserveOnboardingSeenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * Giu trang thai "da xem onboarding" cho AppEntryContent.
 * - null  = chua biet (DataStore chua emit lan dau)
 * - false = chua xem onboarding (lan dau cai)
 * - true  = da xem (di thang Main sau splash)
 */
@HiltViewModel
class AppEntryViewModel @Inject constructor(
    observeOnboardingSeen: ObserveOnboardingSeenUseCase,
) : ViewModel() {

    val onboardingSeen: StateFlow<Boolean?> = observeOnboardingSeen()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null,
        )
}
