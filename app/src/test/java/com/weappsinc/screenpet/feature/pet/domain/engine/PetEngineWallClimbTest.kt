package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PetBorderContact
import com.weappsinc.screenpet.feature.pet.domain.model.PetInput
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetSnapshot
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class PetEngineWallClimbTest {

    @Test
    fun wall_descend_reaches_floor_then_walks() {
        val area = PetPlayArea(320, 520)
        val minX = PetBoundsGeometry.minAnchorX(area)
        val maxY = PetBoundsGeometry.maxAnchorY(area)
        val snap = PetSnapshot(
            anchorXPx = minX,
            anchorYPx = 180f,
            velocityXPxPerSec = 0f,
            velocityYPxPerSec = 0f,
            lookRight = true,
            clipId = ShimejiClipId.ClimbWall,
            frameIndex = 0,
            msAccumulatedInFrame = 0f,
            phase = PetRuntimePhase.WallLeft,
            contact = PetBorderContact(),
            isDragging = false,
            dragGrabOffsetXPx = 0f,
            dragGrabOffsetYPx = 0f,
            bouncePhaseRemainingMs = 0L,
            wallDescend = true,
            perimeterPatrolEnabled = false,
        )
        var w = PetWorldState(area, PetBoundsGeometry.clampAnchor(snap, area))
        repeat(400) {
            w = PetEngine.advance(w, PetInput.Tick(16)).getOrThrow()
        }
        assertEquals(PetRuntimePhase.GroundMoving, w.snapshot.phase)
        assertEquals(ShimejiClipId.Walk, w.snapshot.clipId)
        assertFalse(w.snapshot.wallDescend)
        assertEquals(maxY, w.snapshot.anchorYPx, 2f)
    }
}
