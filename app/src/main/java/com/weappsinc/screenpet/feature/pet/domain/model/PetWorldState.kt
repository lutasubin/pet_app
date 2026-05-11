package com.weappsinc.screenpet.feature.pet.domain.model

/**
 * Vung choi + snapshot pet (SSOT cap nhat boi UseCase).
 *
 * [dragAnchorStartXPx]/[dragAnchorStartYPx]: neo pet luc bat dau keo (null khi khong keo).
 */
data class PetWorldState(
    val playArea: PetPlayArea,
    val snapshot: PetSnapshot,
    val dragAnchorStartXPx: Float? = null,
    val dragAnchorStartYPx: Float? = null,
) {
    fun clearedDragAnchors(): PetWorldState =
        copy(dragAnchorStartXPx = null, dragAnchorStartYPx = null)
}
