package com.weappsinc.screenpet.feature.pet.presentation.overlay

import com.weappsinc.screenpet.core.constants.PetSpriteAnchor
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetId
import com.weappsinc.screenpet.feature.pet.domain.repository.PetArenaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/** Lay anchor cua mot pet trong arena, dich window theo. */
class PetArenaOverlayPositionSync(
    private val scope: CoroutineScope,
    private val repository: PetArenaRepository,
    private val petId: PetId,
    private val host: PetArenaOverlayWindowHost,
) {
    private var job: Job? = null

    fun start() {
        if (job?.isActive == true) return
        job = scope.launch {
            repository.arena.map { it.pets[petId] }.collectLatest { entity ->
                entity ?: return@collectLatest
                val s = entity.snapshot
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
