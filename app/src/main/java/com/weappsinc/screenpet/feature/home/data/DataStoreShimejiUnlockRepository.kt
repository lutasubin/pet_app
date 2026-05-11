package com.weappsinc.screenpet.feature.home.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.weappsinc.screenpet.feature.home.domain.repository.ShimejiUnlockRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class DataStoreShimejiUnlockRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : ShimejiUnlockRepository {

    override val unlockedIds: Flow<Set<String>> = dataStore.data.map { prefs ->
        prefs[HomePreferencesKeys.UnlockedIds].orEmpty()
    }
    override val downloadedIds: Flow<Set<String>> = dataStore.data.map { prefs ->
        prefs[HomePreferencesKeys.DownloadedIds].orEmpty()
    }

    override suspend fun unlock(id: String) {
        dataStore.edit { prefs ->
            val cur = prefs[HomePreferencesKeys.UnlockedIds].orEmpty()
            prefs[HomePreferencesKeys.UnlockedIds] = cur + id
        }
    }

    override suspend fun markDownloaded(id: String) {
        dataStore.edit { prefs ->
            val cur = prefs[HomePreferencesKeys.DownloadedIds].orEmpty()
            prefs[HomePreferencesKeys.DownloadedIds] = cur + id
        }
    }

    override suspend fun replaceAll(ids: Set<String>) {
        dataStore.edit { prefs ->
            prefs[HomePreferencesKeys.UnlockedIds] = ids
        }
    }

    override suspend fun replaceDownloaded(ids: Set<String>) {
        dataStore.edit { prefs ->
            prefs[HomePreferencesKeys.DownloadedIds] = ids
        }
    }
}
