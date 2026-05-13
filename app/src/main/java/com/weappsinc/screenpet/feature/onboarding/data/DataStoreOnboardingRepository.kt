package com.weappsinc.screenpet.feature.onboarding.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.weappsinc.screenpet.feature.onboarding.domain.repository.OnboardingRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class DataStoreOnboardingRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : OnboardingRepository {

    override fun observeSeen(): Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[OnboardingPreferencesKeys.Seen] ?: false
    }

    override suspend fun markSeen() {
        dataStore.edit { it[OnboardingPreferencesKeys.Seen] = true }
    }
}
