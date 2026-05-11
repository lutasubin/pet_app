package com.weappsinc.screenpet.feature.pet.domain.repository

import com.weappsinc.screenpet.feature.pet.domain.petsim.PetArenaState
import kotlinx.coroutines.flow.StateFlow

/** SSOT cho overlay nhieu pet (swarm). */
interface PetArenaRepository {
    val arena: StateFlow<PetArenaState>
    suspend fun replace(newArena: PetArenaState)
}
