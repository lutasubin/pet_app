package com.weappsinc.screenpet.feature.pet.domain.model

import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.engine.PetBoundsGeometry

object PetWorldDefaults {
    fun initial(playArea: PetPlayArea): PetWorldState {
        val cx = playArea.widthPx / 2f
        val ay = playArea.heightPx.toFloat()
        val snap = PetSnapshot(
            anchorXPx = cx,
            anchorYPx = ay,
            velocityXPxPerSec = 0f,
            velocityYPxPerSec = 0f,
            lookRight = true,
            clipId = ShimejiClipId.Walk,
            frameIndex = 0,
            msAccumulatedInFrame = 0f,
            phase = PetRuntimePhase.GroundMoving,
            contact = PetBorderContact(floor = true),
            isDragging = false,
            dragGrabOffsetXPx = 0f,
            dragGrabOffsetYPx = 0f,
            bouncePhaseRemainingMs = 0L,
        )
        return PetWorldState(playArea, PetBoundsGeometry.clampAnchor(snap, playArea))
    }
}
