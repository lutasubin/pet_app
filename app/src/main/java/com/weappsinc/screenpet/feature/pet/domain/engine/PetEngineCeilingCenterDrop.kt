package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PerimeterPatrolStage
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetSnapshot
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState

/**
 * Khi pet di tren tran (CeilingWalk) va vuot qua DIEM GIUA mep tren -> tha roi tu do
 * (vx = 0, vy = 0). Roi gravity + resolveLanding mac dinh se chuyen sang Bouncing -> GroundMoving.
 */
internal object PetEngineCeilingCenterDrop {

    fun tryDropAtCenter(
        prev: PetSnapshot,
        next: PetSnapshot,
        world: PetWorldState,
        vx: Float,
    ): PetSnapshot? {
        if (!prev.perimeterPatrolEnabled) return null
        if (prev.phase != PetRuntimePhase.Ceiling) return null
        if (prev.perimeterStage != PerimeterPatrolStage.CeilingWalk) return null
        val centerX = world.playArea.widthPx / 2f
        val crossed = (vx > 0f && prev.anchorXPx < centerX && next.anchorXPx >= centerX) ||
            (vx < 0f && prev.anchorXPx > centerX && next.anchorXPx <= centerX)
        if (!crossed) return null
        return next.copy(
            anchorXPx = centerX,
            phase = PetRuntimePhase.Airborne,
            clipId = ShimejiClipId.Falling,
            velocityXPxPerSec = 0f,
            velocityYPxPerSec = 0f,
            wallDescend = false,
            perimeterStage = PerimeterPatrolStage.BottomWalk,
            frameIndex = 0,
            msAccumulatedInFrame = 0f,
        )
    }
}
