package com.weappsinc.screenpet.feature.pet.presentation.overlay

import com.weappsinc.screenpet.feature.pet.domain.model.PetInput
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.usecase.DispatchPetInputUseCase
import com.weappsinc.screenpet.feature.pet.domain.usecase.UpdatePlayAreaUseCase
import com.weappsinc.screenpet.feature.pet.presentation.PetPlayEventSink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PetOverlayPlaySink(
    private val scope: CoroutineScope,
    private val updatePlayAreaUseCase: UpdatePlayAreaUseCase,
    private val dispatchPetInputUseCase: DispatchPetInputUseCase,
) : PetPlayEventSink {

    override fun onPlayAreaSized(widthPx: Int, heightPx: Int) {
        if (widthPx <= 0 || heightPx <= 0) return
        scope.launch { updatePlayAreaUseCase(PetPlayArea(widthPx, heightPx)) }
    }

    override fun onPointerDown(xPx: Float, yPx: Float) {
        scope.launch { dispatchPetInputUseCase(PetInput.PointerDown(xPx, yPx)) }
    }

    override fun onPointerMove(xPx: Float, yPx: Float) {
        scope.launch { dispatchPetInputUseCase(PetInput.PointerMove(xPx, yPx)) }
    }

    override fun onPointerUp() {
        scope.launch { dispatchPetInputUseCase(PetInput.PointerUp()) }
    }
}
