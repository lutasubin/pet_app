package com.weappsinc.screenpet.feature.settings.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.weappsinc.screenpet.feature.settings.domain.model.AppSettings
import com.weappsinc.screenpet.feature.settings.domain.repository.AppSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class DataStoreAppSettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : AppSettingsRepository {

    override val settings: Flow<AppSettings> = dataStore.data.map { prefs ->
        AppSettings(
            ghostModeEnabled = prefs[AppSettingsPreferencesKeys.GhostMode] ?: false,
            animationSizeScale = prefs[AppSettingsPreferencesKeys.AnimationSizeScale]
                ?: AppSettings.DEFAULT_ANIMATION_SIZE_SCALE,
            animationSpeedMultiplier = prefs[AppSettingsPreferencesKeys.AnimationSpeedMult]
                ?: AppSettings.DEFAULT_ANIMATION_SPEED_MULT,
            sessionDurationMinutes = prefs[AppSettingsPreferencesKeys.SessionDurationMinutes]
                ?: AppSettings.DEFAULT_SESSION_DURATION_MIN,
            localeTag = prefs[AppSettingsPreferencesKeys.LocaleTag] ?: AppSettings.LOCALE_SYSTEM,
        )
    }

    override suspend fun setGhostMode(enabled: Boolean) {
        dataStore.edit { it[AppSettingsPreferencesKeys.GhostMode] = enabled }
    }

    override suspend fun setAnimationSizeScale(scale: Float) {
        val s = scale.coerceIn(AppSettings.MIN_SIZE_SCALE, AppSettings.MAX_SIZE_SCALE)
        dataStore.edit { it[AppSettingsPreferencesKeys.AnimationSizeScale] = s }
    }

    override suspend fun setAnimationSpeedMultiplier(mult: Float) {
        val m = mult.coerceIn(AppSettings.MIN_SPEED_MULT, AppSettings.MAX_SPEED_MULT)
        dataStore.edit { it[AppSettingsPreferencesKeys.AnimationSpeedMult] = m }
    }

    override suspend fun setSessionDurationMinutes(minutes: Int) {
        val safe = minutes.coerceIn(1, 120)
        dataStore.edit { it[AppSettingsPreferencesKeys.SessionDurationMinutes] = safe }
    }

    override suspend fun setLocaleTag(tag: String) {
        dataStore.edit { it[AppSettingsPreferencesKeys.LocaleTag] = tag }
    }
}
