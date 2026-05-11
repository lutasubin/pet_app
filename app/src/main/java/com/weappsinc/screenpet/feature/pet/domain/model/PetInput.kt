package com.weappsinc.screenpet.feature.pet.domain.model

/** Su kien tu UI hoac dong ho tick. */
sealed class PetInput {
    data class Tick(val dtMs: Long) : PetInput()
    data class Layout(val playArea: PetPlayArea) : PetInput()
    data class PointerDown(val xPx: Float, val yPx: Float) : PetInput()
    data class PointerMove(val xPx: Float, val yPx: Float) : PetInput()
    data class PointerUp(
        val releaseVelocityXPxPerSec: Float = 0f,
        val releaseVelocityYPxPerSec: Float = 0f,
    ) : PetInput()
}
