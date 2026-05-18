package com.weappsinc.screenpet.feature.settings.presentation

import com.weappsinc.screenpet.feature.settings.domain.model.AppSettings
import com.weappsinc.screenpet.feature.settings.domain.repository.AppRatingRepository
import com.weappsinc.screenpet.feature.settings.domain.repository.AppSettingsRepository
import com.weappsinc.screenpet.feature.settings.domain.usecase.ObserveAppSettingsUseCase
import com.weappsinc.screenpet.feature.settings.domain.usecase.ObserveSavedAppRatingUseCase
import com.weappsinc.screenpet.feature.settings.domain.usecase.SaveAppRatingUseCase
import com.weappsinc.screenpet.feature.settings.domain.usecase.SetAnimationSizeScaleUseCase
import com.weappsinc.screenpet.feature.settings.domain.usecase.SetAnimationSpeedMultiplierUseCase
import com.weappsinc.screenpet.feature.settings.domain.usecase.SetGhostModeUseCase
import com.weappsinc.screenpet.feature.settings.domain.usecase.SetLocaleTagUseCase
import com.weappsinc.screenpet.feature.settings.domain.usecase.SetSessionDurationMinutesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class SettingsViewModelTest {

    private class FakeRepo : AppSettingsRepository {
        val flow = MutableStateFlow(AppSettings())
        override val settings = flow
        override suspend fun setGhostMode(enabled: Boolean) {
            flow.update { it.copy(ghostModeEnabled = enabled) }
        }
        override suspend fun setAnimationSizeScale(scale: Float) {
            flow.update { it.copy(animationSizeScale = scale) }
        }
        override suspend fun setAnimationSpeedMultiplier(mult: Float) {
            flow.update { it.copy(animationSpeedMultiplier = mult) }
        }
        override suspend fun setSessionDurationMinutes(minutes: Int) {
            flow.update { it.copy(sessionDurationMinutes = minutes) }
        }
        override suspend fun setLocaleTag(tag: String) {
            flow.update { it.copy(localeTag = tag) }
        }
    }

    private class FakeRatingRepo : AppRatingRepository {
        override val savedStars: Flow<Int?> = flowOf(null)
        override suspend fun saveStars(stars: Int) = Unit
    }

    @Test
    fun ghost_toggle_cap_nhat_state() = runBlocking {
        val fake = FakeRepo()
        val ratingFake = FakeRatingRepo()
        val vm = SettingsViewModel(
            observeAppSettings = ObserveAppSettingsUseCase(fake),
            setGhostMode = SetGhostModeUseCase(fake),
            setAnimationSizeScale = SetAnimationSizeScaleUseCase(fake),
            setAnimationSpeedMultiplier = SetAnimationSpeedMultiplierUseCase(fake),
            setSessionDurationMinutes = SetSessionDurationMinutesUseCase(fake),
            setLocaleTag = SetLocaleTagUseCase(fake),
            observeSavedAppRating = ObserveSavedAppRatingUseCase(ratingFake),
            saveAppRating = SaveAppRatingUseCase(ratingFake),
        )
        val collector = launch { vm.uiState.collect { } }
        vm.onGhostModeChanged(true)
        delay(50)
        assertTrue(vm.uiState.value.ghostModeEnabled)
        collector.cancel()
    }
}
