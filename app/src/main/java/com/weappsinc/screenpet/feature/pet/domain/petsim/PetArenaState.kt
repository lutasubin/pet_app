package com.weappsinc.screenpet.feature.pet.domain.petsim

import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState

/**
 * San chung + map pet -> trang thai the.
 * App hien chi dung [PetId.DEFAULT]; sau them pet: them key + UI truyen [PetId].
 */
data class PetArenaState(
    val playArea: PetPlayArea,
    val pets: Map<PetId, PetEntityState>,
) {
    fun toPrimaryWorld(): PetWorldState {
        val e = pets[PetId.DEFAULT]
            ?: error("PetArenaState thieu PetId.DEFAULT")
        return e.toWorld(playArea)
    }

    companion object {
        fun fromPrimaryWorld(world: PetWorldState): PetArenaState =
            PetArenaState(
                playArea = world.playArea,
                pets = mapOf(PetId.DEFAULT to PetEntityState.fromWorld(world)),
            )
    }
}
