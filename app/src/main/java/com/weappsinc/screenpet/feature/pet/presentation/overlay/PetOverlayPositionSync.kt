package com.weappsinc.screenpet.feature.pet.presentation.overlay

import com.weappsinc.screenpet.core.constants.PetSpriteAnchor
import com.weappsinc.screenpet.feature.pet.domain.repository.PetSimulationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/** Lay anchor pet tu repository, dich window overlay theo. */
class PetOverlayPositionSync(
    private val scope: CoroutineScope,
    private val repository: PetSimulationRepository,
    private val host: PetOverlayWindowHost,
) {
    private var job: Job? = null

    fun start() {
        if (job?.isActive == true) return
        job = scope.launch {
            repository.world.collectLatest { world ->
                val s = world.snapshot
                val ax = PetSpriteAnchor.ANCHOR_X_IN_SPRITE * PetSpriteAnchor.OVERLAY_DISPLAY_SCALE
                val ay = PetSpriteAnchor.ANCHOR_Y_IN_SPRITE * PetSpriteAnchor.OVERLAY_DISPLAY_SCALE
                val x = (s.anchorXPx - ax).toInt()
                val y = (s.anchorYPx - ay).toInt()
                host.update(x, y)
            }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }
}
