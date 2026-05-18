package com.weappsinc.screenpet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.screenpet.feature.onboarding.domain.usecase.ObserveLanguagePickerCompletedUseCase
import com.weappsinc.screenpet.feature.onboarding.domain.usecase.ObserveOnboardingSeenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class AppEntryViewModel @Inject constructor(
    observeOnboardingSeen: ObserveOnboardingSeenUseCase,
    observeLanguagePickerCompleted: ObserveLanguagePickerCompletedUseCase,
) : ViewModel() {

    val onboardingSeen: StateFlow<Boolean?> = observeOnboardingSeen()
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = null)

    val languagePickerCompleted: StateFlow<Boolean?> = observeLanguagePickerCompleted()
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = null)

    /** null = DataStore chua san sang. */
    val postSplashDestination: StateFlow<AppEntryDestination?> = combine(
        onboardingSeen,
        languagePickerCompleted,
    ) { seen, langDone ->
        if (seen == null || langDone == null) null
        else when {
            !langDone -> AppEntryDestination.Language
            !seen -> AppEntryDestination.Onboarding
            else -> AppEntryDestination.Main
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = null)
}
