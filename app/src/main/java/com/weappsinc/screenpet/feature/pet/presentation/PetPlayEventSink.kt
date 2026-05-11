package com.weappsinc.screenpet.feature.pet.presentation

/** Nhan su kien layout/pointer tu UI (Activity hoac overlay). */
interface PetPlayEventSink {
    fun onPlayAreaSized(widthPx: Int, heightPx: Int)
    fun onPointerDown(xPx: Float, yPx: Float)
    fun onPointerMove(xPx: Float, yPx: Float)
    fun onPointerUp(releaseVelocityXPxPerSec: Float = 0f, releaseVelocityYPxPerSec: Float = 0f)
}
