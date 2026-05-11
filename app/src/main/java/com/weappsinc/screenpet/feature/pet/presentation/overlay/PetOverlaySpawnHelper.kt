package com.weappsinc.screenpet.feature.pet.presentation.overlay

import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.repository.PetSimulationRepository
import com.weappsinc.screenpet.feature.pet.domain.sync.PetSimulationUpdateMutex
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.withLock

/** Dat pet ra giua man hinh khi overlay vua bat (tranh khuat nav bar). */
object PetOverlaySpawnHelper {
    fun spawnAtCenter(
        scope: CoroutineScope,
        repository: PetSimulationRepository,
        writeMutex: PetSimulationUpdateMutex,
        screenWidthPx: Int,
        screenHeightPx: Int,
    ) {
        val cx = screenWidthPx / 2f
        val cy = screenHeightPx / 2f
        scope.launch {
            writeMutex.mutex.withLock {
                val cur = repository.world.value
                val s = cur.snapshot.copy(
                    anchorXPx = cx,
                    anchorYPx = cy,
                    velocityXPxPerSec = 0f,
                    velocityYPxPerSec = 0f,
                    phase = PetRuntimePhase.Airborne,
                    clipId = ShimejiClipId.Falling,
                    isDragging = false,
                    frameIndex = 0,
                    msAccumulatedInFrame = 0f,
                )
                repository.replace(cur.copy(snapshot = s))
            }
        }
    }
}
