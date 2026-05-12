package com.weappsinc.screenpet.feature.settings.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

/** Khoa DataStore cho AppSettings (cung file home_prefs). */
object AppSettingsPreferencesKeys {
    val GhostMode = booleanPreferencesKey("settings_ghost_mode")
    val AnimationSizeScale = floatPreferencesKey("settings_anim_size_scale")
    val AnimationSpeedMult = floatPreferencesKey("settings_anim_speed_mult")
    val SessionDurationMinutes = intPreferencesKey("settings_session_duration_min")
    val LocaleTag = stringPreferencesKey("settings_locale_tag")
}
