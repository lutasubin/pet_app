package com.weappsinc.screenpet.feature.pet.domain.petsim

import com.weappsinc.screenpet.core.constants.PetAssetPaths
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.model.PetSnapshot
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState

/**
 * Snapshot + phien keo + thu muc asset cua mot pet (vi du "img" hoac "data1/Ace").
 * Hai pet co the dung thu muc khac nhau (swarm mode).
 */
data class PetEntityState(
    val snapshot: PetSnapshot,
    val dragAnchorStartXPx: Float? = null,
    val dragAnchorStartYPx: Float? = null,
    val assetFolder: String = PetAssetPaths.SHIME_IMAGE_FOLDER,
) {
    fun toWorld(playArea: PetPlayArea): PetWorldState =
        PetWorldState(playArea, snapshot, dragAnchorStartXPx, dragAnchorStartYPx)

    companion object {
        fun fromWorld(
            world: PetWorldState,
            assetFolder: String = PetAssetPaths.SHIME_IMAGE_FOLDER,
        ): PetEntityState =
            PetEntityState(
                snapshot = world.snapshot,
                dragAnchorStartXPx = world.dragAnchorStartXPx,
                dragAnchorStartYPx = world.dragAnchorStartYPx,
                assetFolder = assetFolder,
            )
    }
}
