package com.weappsinc.screenpet.feature.onboarding.data

import androidx.datastore.preferences.core.booleanPreferencesKey

/** Khoa DataStore cho onboarding — ghi chung file `home_prefs`. */
object OnboardingPreferencesKeys {
    val Seen = booleanPreferencesKey("onboarding_seen")
}
