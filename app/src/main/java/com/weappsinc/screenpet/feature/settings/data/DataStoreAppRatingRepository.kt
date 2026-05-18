package com.weappsinc.screenpet.feature.settings.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.weappsinc.screenpet.feature.settings.domain.repository.AppRatingRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class DataStoreAppRatingRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : AppRatingRepository {

    override val savedStars: Flow<Int?> = dataStore.data.map { prefs ->
        val raw = prefs[AppRatingPreferencesKeys.SavedStars] ?: return@map null
        if (raw < 0) null else raw.coerceIn(0, 5)
    }

    override suspend fun saveStars(stars: Int) {
        val safe = stars.coerceIn(0, 5)
        dataStore.edit { it[AppRatingPreferencesKeys.SavedStars] = safe }
    }
}
