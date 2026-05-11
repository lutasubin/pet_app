package com.weappsinc.screenpet.feature.pet.domain.model

/**
 * Vung choi + snapshot pet (SSOT cap nhat boi UseCase).
 */
data class PetWorldState(
    val playArea: PetPlayArea,
    val snapshot: PetSnapshot,
)
