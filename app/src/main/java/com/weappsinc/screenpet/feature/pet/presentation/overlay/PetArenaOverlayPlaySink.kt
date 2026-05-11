package com.weappsinc.screenpet.feature.pet.presentation.overlay

import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetArenaInput
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetId
import com.weappsinc.screenpet.feature.pet.domain.usecase.DispatchArenaInputUseCase
import com.weappsinc.screenpet.feature.pet.domain.usecase.UpdateArenaPlayAreaUseCase
import com.weappsinc.screenpet.feature.pet.presentation.PetPlayEventSink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/** Adapter PetPlayEventSink cho arena: gan voi mot PetId co dinh. */
class PetArenaOverlayPlaySink(
    private val scope: CoroutineScope,
    private val petId: PetId,
    private val updatePlayArea: UpdateArenaPlayAreaUseCase,
    private val dispatchArenaInput: DispatchArenaInputUseCase,
) : PetPlayEventSink {

    override fun onPlayAreaSized(widthPx: Int, heightPx: Int) {
        if (widthPx <= 0 || heightPx <= 0) return
        scope.launch { updatePlayArea(PetPlayArea(widthPx, heightPx)) }
    }

    override fun onPointerDown(xPx: Float, yPx: Float) {
        scope.launch { dispatchArenaInput(PetArenaInput.PointerDown(petId, xPx, yPx)) }
    }

    override fun onPointerMove(xPx: Float, yPx: Float) {
        scope.launch { dispatchArenaInput(PetArenaInput.PointerMove(petId, xPx, yPx)) }
    }

    override fun onPointerUp(releaseVelocityXPxPerSec: Float, releaseVelocityYPxPerSec: Float) {
        scope.launch {
            dispatchArenaInput(
                PetArenaInput.PointerUp(petId, releaseVelocityXPxPerSec, releaseVelocityYPxPerSec),
            )
        }
    }
}
