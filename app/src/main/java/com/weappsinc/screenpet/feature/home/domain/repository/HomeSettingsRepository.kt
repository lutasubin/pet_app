package com.weappsinc.screenpet.feature.home.domain.repository

import com.weappsinc.screenpet.feature.home.domain.model.HomeSettings
import kotlinx.coroutines.flow.Flow

/** Cau hinh man Home (activate, swarm, slot). */
interface HomeSettingsRepository {
    val settings: Flow<HomeSettings>
    suspend fun setActivate(enabled: Boolean)
    suspend fun setSwarm(enabled: Boolean)
    suspend fun setSlot(slot: Int, characterId: String?)
    suspend fun setUnlockedSlotCount(count: Int)
}
