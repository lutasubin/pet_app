package com.weappsinc.screenpet.feature.settings.domain.repository

import com.weappsinc.screenpet.feature.settings.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {
    val settings: Flow<AppSettings>

    suspend fun setGhostMode(enabled: Boolean)
    suspend fun setAnimationSizeScale(scale: Float)
    suspend fun setAnimationSpeedMultiplier(mult: Float)
    suspend fun setSessionDurationMinutes(minutes: Int)
    suspend fun setLocaleTag(tag: String)
}
