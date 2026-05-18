package com.weappsinc.screenpet.feature.settings.data

import androidx.datastore.preferences.core.intPreferencesKey

object AppRatingPreferencesKeys {
    /** -1 = chua luu; 0..5 = da danh gia. */
    val SavedStars = intPreferencesKey("settings_app_rating_stars")
}
