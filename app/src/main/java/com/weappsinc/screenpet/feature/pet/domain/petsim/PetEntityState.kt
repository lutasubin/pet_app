package com.weappsinc.screenpet.feature.pet.domain.petsim

import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.model.PetSnapshot
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState

/** Snapshot + phien keo cua mot pet; doc lap voi pet khac trong cung san. */
data class PetEntityState(
    val snapshot: PetSnapshot,
    val dragAnchorStartXPx: Float? = null,
    val dragAnchorStartYPx: Float? = null,
) {
    fun toWorld(playArea: PetPlayArea): PetWorldState =
        PetWorldState(playArea, snapshot, dragAnchorStartXPx, dragAnchorStartYPx)

    companion object {
        fun fromWorld(world: PetWorldState): PetEntityState =
            PetEntityState(
                snapshot = world.snapshot,
                dragAnchorStartXPx = world.dragAnchorStartXPx,
                dragAnchorStartYPx = world.dragAnchorStartYPx,
            )
    }
}
