package com.weappsinc.screenpet.feature.home.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.weappsinc.screenpet.feature.home.domain.model.HomeSettings

/** Khoa DataStore cho Home settings va unlock state. */
object HomePreferencesKeys {
    const val DATASTORE_NAME: String = "home_prefs"

    val UnlockedIds = stringSetPreferencesKey("unlocked_ids")
    val DownloadedIds = stringSetPreferencesKey("downloaded_ids")
    val Activate = booleanPreferencesKey("activate")
    val Swarm = booleanPreferencesKey("swarm")
    val UnlockedSlotCount = intPreferencesKey("unlocked_slot_count")
    val MixRandomPetCount = intPreferencesKey("mix_random_pet_count")

    val SlotKeys: List<androidx.datastore.preferences.core.Preferences.Key<String>> =
        (0 until HomeSettings.SLOT_COUNT).map { stringPreferencesKey("slot_$it") }
}
