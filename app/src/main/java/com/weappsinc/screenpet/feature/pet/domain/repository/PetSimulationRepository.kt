package com.weappsinc.screenpet.feature.pet.domain.repository

import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState
import kotlinx.coroutines.flow.StateFlow

/**
 * SSOT trang thai mo phong (mot pet, dang [PetWorldState]).
 * Nhieu pet: chuyen repository sang [com.weappsinc.screenpet.feature.pet.domain.petsim.PetArenaState].
 */
interface PetSimulationRepository {
    val world: StateFlow<PetWorldState>
    suspend fun replace(newWorld: PetWorldState)
}
