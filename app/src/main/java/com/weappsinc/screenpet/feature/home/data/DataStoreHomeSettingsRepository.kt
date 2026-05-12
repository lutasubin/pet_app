package com.weappsinc.screenpet.feature.home.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.weappsinc.screenpet.feature.home.domain.model.HomeSettings
import com.weappsinc.screenpet.feature.home.domain.repository.HomeSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class DataStoreHomeSettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : HomeSettingsRepository {

    override val settings: Flow<HomeSettings> = dataStore.data.map { prefs ->
        HomeSettings(
            activateEnabled = prefs[HomePreferencesKeys.Activate] ?: false,
            swarmEnabled = prefs[HomePreferencesKeys.Swarm] ?: false,
            selectedSlotIds = HomePreferencesKeys.SlotKeys.map { prefs[it] },
            unlockedSlotCount = prefs[HomePreferencesKeys.UnlockedSlotCount] ?: 1,
            mixRandomPetCount = prefs[HomePreferencesKeys.MixRandomPetCount]?.coerceIn(1, HomeSettings.SLOT_COUNT) ?: 1,
        )
    }

    override suspend fun setActivate(enabled: Boolean) {
        dataStore.edit { it[HomePreferencesKeys.Activate] = enabled }
    }

    override suspend fun setSwarm(enabled: Boolean) {
        dataStore.edit { it[HomePreferencesKeys.Swarm] = enabled }
    }

    override suspend fun setSlot(slot: Int, characterId: String?) {
        require(slot in 0 until HomeSettings.SLOT_COUNT) { "slot ngoai pham vi" }
        dataStore.edit { prefs ->
            val key = HomePreferencesKeys.SlotKeys[slot]
            if (characterId == null) prefs.remove(key) else prefs[key] = characterId
        }
    }

    override suspend fun setUnlockedSlotCount(count: Int) {
        val safeCount = count.coerceIn(1, HomeSettings.SLOT_COUNT)
        dataStore.edit { prefs ->
            prefs[HomePreferencesKeys.UnlockedSlotCount] = safeCount
        }
    }

    override suspend fun setMixRandomPetCount(count: Int) {
        val safe = count.coerceIn(1, HomeSettings.SLOT_COUNT)
        dataStore.edit { it[HomePreferencesKeys.MixRandomPetCount] = safe }
    }
}
