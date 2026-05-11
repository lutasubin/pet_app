package com.weappsinc.screenpet.feature.pet.domain.petsim

import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea

/** Su kien mo phong huong toi mot hoac tat ca pet trong arena. */
sealed class PetArenaInput {
    data class TickAll(val dtMs: Long) : PetArenaInput()
    data class Layout(val playArea: PetPlayArea) : PetArenaInput()
    data class PointerDown(val petId: PetId, val xPx: Float, val yPx: Float) : PetArenaInput()
    data class PointerMove(val petId: PetId, val xPx: Float, val yPx: Float) : PetArenaInput()
    data class PointerUp(
        val petId: PetId,
        val releaseVelocityXPxPerSec: Float = 0f,
        val releaseVelocityYPxPerSec: Float = 0f,
    ) : PetArenaInput()
}
