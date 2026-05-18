package com.weappsinc.screenpet.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.screenpet.feature.settings.domain.model.AppSettings
import com.weappsinc.screenpet.feature.settings.domain.usecase.ObserveAppSettingsUseCase
import com.weappsinc.screenpet.feature.settings.domain.usecase.SetAnimationSizeScaleUseCase
import com.weappsinc.screenpet.feature.settings.domain.usecase.SetAnimationSpeedMultiplierUseCase
import com.weappsinc.screenpet.feature.settings.domain.usecase.SetGhostModeUseCase
import com.weappsinc.screenpet.feature.settings.domain.usecase.ObserveSavedAppRatingUseCase
import com.weappsinc.screenpet.feature.settings.domain.usecase.SaveAppRatingUseCase
import com.weappsinc.screenpet.feature.settings.domain.usecase.SetLocaleTagUseCase
import com.weappsinc.screenpet.feature.settings.domain.usecase.SetSessionDurationMinutesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    observeAppSettings: ObserveAppSettingsUseCase,
    private val setGhostMode: SetGhostModeUseCase,
    private val setAnimationSizeScale: SetAnimationSizeScaleUseCase,
    private val setAnimationSpeedMultiplier: SetAnimationSpeedMultiplierUseCase,
    private val setSessionDurationMinutes: SetSessionDurationMinutesUseCase,
    private val setLocaleTag: SetLocaleTagUseCase,
    observeSavedAppRating: ObserveSavedAppRatingUseCase,
    private val saveAppRating: SaveAppRatingUseCase,
) : ViewModel() {

    val savedRatingStars: StateFlow<Int?> = observeSavedAppRating()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val uiState: StateFlow<AppSettings> = observeAppSettings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppSettings())

    fun onGhostModeChanged(enabled: Boolean) {
        viewModelScope.launch { setGhostMode(enabled) }
    }

    fun onAnimationSizeChangeFinished(scale: Float) {
        viewModelScope.launch { setAnimationSizeScale(scale) }
    }

    fun onAnimationSpeedChangeFinished(mult: Float) {
        viewModelScope.launch { setAnimationSpeedMultiplier(mult) }
    }

    fun onSessionDurationSelected(minutes: Int) {
        viewModelScope.launch { setSessionDurationMinutes(minutes) }
    }

    suspend fun persistLocaleTag(tag: String) {
        setLocaleTag(tag)
    }

    fun onRatingSubmitted(stars: Int) {
        viewModelScope.launch { saveAppRating(stars) }
    }
}
